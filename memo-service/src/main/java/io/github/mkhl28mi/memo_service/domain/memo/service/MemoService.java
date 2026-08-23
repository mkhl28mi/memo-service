package io.github.mkhl28mi.memo_service.domain.memo.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import io.github.mkhl28mi.memo_service.domain.admin.department.dto.response.DepartmentResponse;
import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.dto.response.EmployeeAssignmentResponse;
import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.entity.EmployeeAssignment;
import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.service.EmployeeAssignmentService;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.dto.response.UserAssignmentResponse;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.entity.UserAssignment;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.service.UserAssignmentService;
import io.github.mkhl28mi.memo_service.domain.admin.user.entity.User;
import io.github.mkhl28mi.memo_service.domain.memo.dto.request.MemoRequest;
import io.github.mkhl28mi.memo_service.domain.memo.dto.response.MemoResponse;
import io.github.mkhl28mi.memo_service.domain.memo.employee.dto.response.MemoEmployeeResponse;
import io.github.mkhl28mi.memo_service.domain.memo.employee.entity.MemoEmployee;
import io.github.mkhl28mi.memo_service.domain.memo.employee.entity.MemoEmployee.Role;
import io.github.mkhl28mi.memo_service.domain.memo.entity.Memo;
import io.github.mkhl28mi.memo_service.domain.memo.label.dto.response.MemoLabelResponse;
import io.github.mkhl28mi.memo_service.domain.memo.label.entity.MemoLabel;
import io.github.mkhl28mi.memo_service.domain.memo.log.entity.MemoLog;
import io.github.mkhl28mi.memo_service.domain.memo.repository.MemoRepository;
import io.github.mkhl28mi.memo_service.exception.BusinessException;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;
import jakarta.validation.constraints.NotNull;

@Service
@Transactional(readOnly = true)
public class MemoService {
	
	private final MemoRepository memoRepository;
	
	private final EmployeeAssignmentService employeeAssignmentService;
	
	private final UserAssignmentService userAssignmentService;
	
	public MemoService(MemoRepository memoRepository, EmployeeAssignmentService employeeAssignmentService, UserAssignmentService userAssignmentService) {
		super();
		this.memoRepository = memoRepository;
		this.employeeAssignmentService = employeeAssignmentService;
		this.userAssignmentService = userAssignmentService;
	}
	
	public MemoResponse getMemoById(@NotNull UUID memoId) {
		var memo = memoRepository.findById(memoId)
				.orElseThrow(() -> new ResourceNotFoundException("Memo not found with id: " + memoId));
		
		memo.initializeMemoEmployees();
		
		memo.initializeMemoLabels();
		
		var employees = memo.getMemoEmployees().stream()
				.map(me -> new MemoEmployeeResponse(me.getId(), 
						new EmployeeAssignmentResponse(me.getEmployeeAssignment()), 
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
						new UserAssignmentResponse(l.getCreatedBy()), 
						l.getName(), 
						l.getCreatedAt()))
				.toList();
		
		return new MemoResponse(memo.getId(),
				memo.getContent(), 
				memo.getStatus(), 
				new UserAssignmentResponse(memo.getAssignee()), 
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
	public UUID createMemo(User user, MemoRequest memoRequest) throws BusinessException {
		UserAssignment currentUserAssgnment = userAssignmentService.getCurrentUserAssignmentByUserId(user.getId());
		
		UserAssignment assignee = userAssignmentService.getUserAssignmentById(memoRequest.assigneeId());
		
        if (!Objects.equals(assignee.getDepartmentUnit().getDepartment(), currentUserAssgnment.getDepartmentUnit().getDepartment())) {
        	throw new BusinessException("Assignee`s departmnet must be the same as user`s departmant");
        }
        
		int currentYear = LocalDate.now().getYear();
		
        int maxNumber = memoRepository.searchMaxSequenceNumber(currentYear, assignee.getDepartmentUnit().getDepartment()).orElse(0);
        int nextNumber = maxNumber + 1;
        
		Memo memo = new Memo(memoRequest.content(), 
				Memo.Status.IN_PROGRESS, 
				assignee, 
				assignee.getDepartmentUnit().getDepartment(), 
				nextNumber, 
				currentYear);
		
		addMemoEmployees(user, memo, memoRequest.copyRecipientIds(), MemoEmployee.Role.COPY_RECIPIENT);
		
		addMemoEmployees(user, memo, memoRequest.recipientIds(), MemoEmployee.Role.RECIPIENT);
		
		addMemoEmployees(user, memo, memoRequest.approverIds(), MemoEmployee.Role.APPROVER);
		
		addMemoEmployees(user, memo, memoRequest.signerIds(), MemoEmployee.Role.SIGNER);
		
		addMemoLabels(user, currentUserAssgnment, memoRequest, memo);
		
		memo.addMemoLog(new MemoLog(memo, currentUserAssgnment, MemoLog.Status.CREATED));
		
		return memoRepository.save(memo).getId();
	}
	
	private void addMemoEmployees(User user, Memo memo, List<UUID> ids, MemoEmployee.Role role) throws ResourceNotFoundException {
		List<EmployeeAssignment> employeeAssignments = employeeAssignmentService.getEmployeeAssignmentsByIds(ids);
		
		Map<UUID, EmployeeAssignment> employeeAssignmentsMap = employeeAssignments.stream()
	            .collect(Collectors.toMap(EmployeeAssignment::getId, e -> e));
		
		int order = 0;
		
	    for (UUID id : ids) {
	    	EmployeeAssignment employeeAssignment = employeeAssignmentsMap.get(id);
	        
	        if (employeeAssignment == null) {
	        	throw new ResourceNotFoundException("EmployeeAssignment not found with ID: " + id + " for user ID: " + user.getId());
	        }
	        
	        Assert.state(employeeAssignment.getEndDate().isEmpty(), "EmployeeAssignment with ID: " + id + " must be current for user ID: " + user.getId());
	        
	        memo.addMemoEmployee(new MemoEmployee(memo, employeeAssignment, role, order++));
	    }
	}
	
	private void addMemoLabels(User user, UserAssignment userAssignment, MemoRequest memoRequest, Memo memo) {
		for (String label : memoRequest.labels()) {
			Assert.hasText(label, () -> "Label for user '" + user.getId() + "' must not be empty");
			
			memo.addMemoLabel(new MemoLabel(memo, userAssignment, label));
		}
	}
	
	@Transactional
	public void updateMemo(User user, UUID memoId, MemoRequest memoRequest) throws BusinessException {
//		Memo memo = memoRepository.findById(memoId)
//				.orElseThrow(() -> new ResourceNotFoundException("Memo not found with ID: " + memoId));
//		
//		Assert.state(memo.getStatus() == Status.IN_PROGRESS, () -> "To update memo it must be with Status: " + Status.IN_PROGRESS);
//		
//		memo.setContent(memoRequest.content());
//		
//		if (!Objects.equals(memo.getAssignee().getId(), memoRequest.assigneeId())) {
//	        User assignee = userService.getEnabledUserById(memoRequest.assigneeId());
//	        
//	        if (!Objects.equals(assignee.getDepartmentUnit().getDepartment(), user.getDepartmentUnit().getDepartment())) {
//	        	throw new BusinessException("Assignee`s departmnet must be the same as user`s departmant");
//	        }
//	        
//			memo.setAssignee(assignee);
//			memo.setDepartmentUnit(assignee.getDepartmentUnit());
//			memo.setDepartment(assignee.getDepartmentUnit().getDepartment());
//		}
//		
//		memo.initializeMemoEmployees();
//		memo.initializeMemoLabels();
//		memo.initializeMemoLogs();
//		
//		updateMemoEmployees(user, memo, memoRequest.copyRecipientIds(), Role.COPY_RECIPIENT);
//		
//		updateMemoEmployees(user, memo, memoRequest.recipientIds(), Role.RECIPIENT);
//		
//		updateMemoEmployees(user, memo, memoRequest.approverIds(), Role.APPROVER);
//		
//		updateMemoEmployees(user, memo, memoRequest.signerIds(), Role.SIGNER);
//		
//		new HashSet<>(memo.getMemoLabels()).forEach(memo::removeMemoLabel);
//		
//		addMemoLabels(user, memoRequest, memo);
//
//		memo.addMemoLog(new MemoLog(memo, user, user.getDepartmentUnit(), MemoLog.Status.EDITED));
//		
//		memoRepository.save(memo);
	}
	
	private void updateMemoEmployees(User user, Memo memo, List<String> newIds, Role role) throws ResourceNotFoundException {
//		var newIdsSet = new HashSet<>(newIds);
//		
//		var existingEmployees = memo.getMemoEmployees().stream()
//	            .filter(me -> me.getRole() == role)
//	            .collect(Collectors.toMap(
//	                me -> me.getEmployee().getId() + ":" + me.getPosition().getId(), 
//	                me -> me
//	            ));
//		
//		existingEmployees.forEach((id, memoEmployee) -> {
//	        if (!newIdsSet.contains(id)) {
//	            memo.removeMemoEmployee(memoEmployee);
//	        }
//	    });
//		
//		var toAdd = newIds.stream()
//	            .filter(id -> !existingEmployees.containsKey(id))
//	            .toList();
//		
//		if (!toAdd.isEmpty()) {
//	        addMemoEmployees(user, memo, toAdd, role);
//	    }
//		
//		var currentEmployeesMap = memo.getMemoEmployees().stream()
//	            .filter(me -> me.getRole() == role)
//	            .collect(Collectors.toMap(
//	                me -> me.getEmployee().getId() + ":" + me.getPosition().getId(), 
//	                me -> me
//	            )); // TODO fix with several the same employees
//		
//		for (int i = 0; i < newIds.size(); i++) {
//	        String id = newIds.get(i);
//	        
//	        MemoEmployee memoEmployee = currentEmployeesMap.get(id);
//
//	        if (memoEmployee == null) {
//	            throw new ResourceNotFoundException("MemoEmployee not found with ID: " + id + " for user ID: " + user.getId());
//	        }
//
//	        memoEmployee.setPlacementOrder(i);
//	    }
	}
	
}
