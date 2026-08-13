package io.github.mkhl28mi.memo_service.domain.memo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import io.github.mkhl28mi.memo_service.domain.department.dto.response.DepartmentResponse;
import io.github.mkhl28mi.memo_service.domain.department_unit.dto.response.DepartmentUnitResponse;
import io.github.mkhl28mi.memo_service.domain.employee.dto.response.EmployeeBasicResponse;
import io.github.mkhl28mi.memo_service.domain.employee.entity.Employee;
import io.github.mkhl28mi.memo_service.domain.employee.service.EmployeeService;
import io.github.mkhl28mi.memo_service.domain.memo.dto.EmployeePosDto;
import io.github.mkhl28mi.memo_service.domain.memo.dto.request.MemoRequest;
import io.github.mkhl28mi.memo_service.domain.memo.dto.response.MemoResponse;
import io.github.mkhl28mi.memo_service.domain.memo.entity.Memo;
import io.github.mkhl28mi.memo_service.domain.memo.repository.MemoRepository;
import io.github.mkhl28mi.memo_service.domain.memo_employee.dto.response.MemoEmployeeResponse;
import io.github.mkhl28mi.memo_service.domain.memo_employee.entity.MemoEmployee;
import io.github.mkhl28mi.memo_service.domain.memo_employee.entity.MemoEmployee.Role;
import io.github.mkhl28mi.memo_service.domain.memo_label.dto.response.MemoLabelResponse;
import io.github.mkhl28mi.memo_service.domain.memo_label.entity.MemoLabel;
import io.github.mkhl28mi.memo_service.domain.memo_log.entity.MemoLog;
import io.github.mkhl28mi.memo_service.domain.position.dto.response.PositionResponse;
import io.github.mkhl28mi.memo_service.domain.position.entity.Position;
import io.github.mkhl28mi.memo_service.domain.user.dto.response.UserResponse;
import io.github.mkhl28mi.memo_service.domain.user.entity.User;
import io.github.mkhl28mi.memo_service.domain.user.service.UserService;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;
import jakarta.validation.constraints.NotNull;

@Service
@Transactional(readOnly = true)
public class MemoService {
	
	private final MemoRepository memoRepository;
	
	private final UserService userService;
	
	private final EmployeeService employeeService;
	
	public MemoService(MemoRepository memoRepository, UserService userService, EmployeeService employeeService) {
		this.memoRepository = memoRepository;
		this.userService = userService;
		this.employeeService = employeeService;
	}
	
	public MemoResponse getMemoById(@NotNull UUID memoId) {
		var memo = memoRepository.findById(memoId)
				.orElseThrow(() -> new ResourceNotFoundException("Memo not found with id: " + memoId));
		
		memo.initializeMemoEmployees();
		memo.initializeMemoLabels();
		
		var employees = memo.getMemoEmployees().stream()
				.map(me -> new MemoEmployeeResponse(me.getId(), 
						new EmployeeBasicResponse(me.getEmployee()), 
						new PositionResponse(me.getPosition()), 
						me.getRole(), 
						me.getPlacementOrder(), 
						me.getCreatedAt()))
				.collect(Collectors.groupingBy(
						MemoEmployeeResponse::role, 
						() -> new EnumMap<>(Role.class),
						Collectors.collectingAndThen(
			                    Collectors.toList(),
			                    list -> {
			                        list.sort(Comparator.comparing(MemoEmployeeResponse::placementOrder));
			                        return list;
			                    }
			               )));
		
		var labels = memo.getMemoLabels().stream()
				.map(l -> new MemoLabelResponse(l.getId(),
						new UserResponse(l.getCreatedBy()), 
						new DepartmentUnitResponse(l.getDepartmentUnit()), 
						l.getName(), 
						l.getCreatedAt()))
				.toList();
		
		return new MemoResponse(memo.getId(),
				memo.getContent(), 
				memo.getStatus(), 
				new UserResponse(memo.getAssignee()), 
				new DepartmentUnitResponse(memo.getDepartmentUnit()), 
				new DepartmentResponse(memo.getDepartment()), 
				memo.getSequenceNumber(), 
				memo.getCreationYear(), 
				memo.getCreatedAt(), 
				memo.getUpdatedAt(), 
				employees.getOrDefault(Role.RECIPIENT, Collections.emptyList()), 
				employees.getOrDefault(Role.COPY_RECIPIENT, Collections.emptyList()), 
				employees.getOrDefault(Role.SIGNER, Collections.emptyList()), 
				employees.getOrDefault(Role.APPROVER, Collections.emptyList()), 
				labels);
	}
	
	@Retryable(includes = { DataIntegrityViolationException.class }, maxRetries = 5)
	@Transactional
	public UUID createMemo(User user, MemoRequest memoRequest) {
        User assignee = userService.getEnabledUserByIdAndDepartment(memoRequest.assigneeId(), user.getDepartmentUnit().getDepartment());
        
		int currentYear = LocalDate.now().getYear();
		
        int maxNumber = memoRepository.searchMaxSequenceNumber(currentYear, assignee.getDepartmentUnit().getDepartment()).orElse(0);
        int nextNumber = maxNumber + 1;
        
		Memo memo = new Memo(memoRequest.content(), 
				Memo.Status.DRAFT, 
				assignee, 
				assignee.getDepartmentUnit(), 
				assignee.getDepartmentUnit().getDepartment(), 
				nextNumber, 
				currentYear);
		
		addMemoEmployees(user, memo, memoRequest.copyRecipientIds(), MemoEmployee.Role.COPY_RECIPIENT);
		
		addMemoEmployees(user, memo, memoRequest.recipientIds(), MemoEmployee.Role.RECIPIENT);
		
		addMemoEmployees(user, memo, memoRequest.approverIds(), MemoEmployee.Role.APPROVER);
		
		addMemoEmployees(user, memo, memoRequest.signerIds(), MemoEmployee.Role.SIGNER);
		
		for (String label : memoRequest.labels()) {
			Assert.hasText(label, () -> "Label for user '" + user.getId() + "' must not be empty");
			memo.addMemoLabel(new MemoLabel(memo, user, user.getDepartmentUnit(), label));
		}
		
		memo.addMemoLog(new MemoLog(memo, user, user.getDepartmentUnit(), MemoLog.Status.CREATED));
		
		return memoRepository.save(memo).getId();
	}
	
	private void addMemoEmployees(User user, Memo memo, List<String> ids, MemoEmployee.Role role) {
		var parsedTargets = new ArrayList<EmployeePosDto>();
		var employeeIds = new HashSet<UUID>();
		
		for (String id : ids) {
			Assert.hasText(id, () -> role + " id is required for user ID: " + user.getId());

	        String[] parts = id.split(":");
	        Assert.isTrue(parts.length == 2, () -> "Employee ID and position ID are required for user ID: " + user.getId());

	        UUID empId = parseUuid(parts[0], "Invalid employee UUID for user ID: " + user.getId());
	        UUID posId = parseUuid(parts[1], "Invalid position UUID for user ID: " + user.getId());

	        parsedTargets.add(new EmployeePosDto(empId, posId));
	        employeeIds.add(empId);
		}
		
		List<Employee> employees = employeeService.getEnabledEmployeesByIds(employeeIds);
		
		Map<UUID, Employee> employeeMap = employees.stream()
	            .collect(Collectors.toMap(Employee::getId, e -> e, (e1, e2) -> e1));
		
		int order = 0;
		
	    for (EmployeePosDto target : parsedTargets) {
	        Employee employee = employeeMap.get(target.employeeId());
	        
	        if (employee == null) {
	            throw new IllegalArgumentException("Employee not found with ID: " + target.employeeId() + " for user ID: " + user.getId());
	        }

	        Position position = employee.getPositions().stream()
	                .filter(p -> p.getId().equals(target.positionId()))
	                .findFirst()
	                .orElseThrow(() -> new IllegalArgumentException("Position not found with ID: " + target.positionId() + " for user ID: " + user.getId()));

	        memo.addMemoEmployee(new MemoEmployee(memo, employee, position, role, order++));
	    }
	}
	
	private UUID parseUuid(String value, String errorMessage) {
	    try {
	        return UUID.fromString(value);
	    } catch (IllegalArgumentException e) {
	        throw new IllegalArgumentException(errorMessage, e);
	    }
	}
	
}
