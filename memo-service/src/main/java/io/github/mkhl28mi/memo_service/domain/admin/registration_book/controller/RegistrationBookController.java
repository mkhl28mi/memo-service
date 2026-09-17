package io.github.mkhl28mi.memo_service.domain.admin.registration_book.controller;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.mkhl28mi.memo_service.domain.admin.department.service.DepartmentService;
import io.github.mkhl28mi.memo_service.domain.admin.employee.service.EmployeeService;
import io.github.mkhl28mi.memo_service.domain.admin.registration_book.service.RegistrationBookService;
import io.github.mkhl28mi.memo_service.domain.admin.user.service.UserService;
import io.github.mkhl28mi.memo_service.domain.memo.dto.request.MemoFilter;
import io.github.mkhl28mi.memo_service.domain.memo.dto.response.MemoResponse;

@Controller
@RequestMapping("/admin/registration-book")
@PreAuthorize("hasAnyRole('ADMIN')")
public class RegistrationBookController {
	
	private static final int PAGE_SIZE = 20;
	
	private final RegistrationBookService registrationBookService;
	
	private final UserService userService;
	
	private final EmployeeService employeeService;
	
	private final DepartmentService departmentService;
	
	public RegistrationBookController(RegistrationBookService registrationBookService, UserService userService, EmployeeService employeeService, DepartmentService departmentService) {
		super();
		this.registrationBookService = registrationBookService;
		this.userService = userService;
		this.employeeService = employeeService;
		this.departmentService = departmentService;
	}
	
	@GetMapping
	public String showDepartments(Model model) {
		model.addAttribute("activePage", "admin/registration-book");
		model.addAttribute("departmentResponses", departmentService.getDepartments(null));
		
		return "admin/registration-book/departments";
	}
	
	@GetMapping("/{departmentId}")
	public String showRegistrationBookByDepartmentId(@PathVariable UUID departmentId, Model model,
			@RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "id") String sortBy,
	        @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
	        @RequestParam(required = false) UUID recipientId,
	        @RequestParam(required = false) UUID assigneeId,
	        @RequestParam(required = false) Integer year,
	        @RequestParam(required = false) Integer month,
	        @RequestParam(required = false) String keyword,
	        @RequestParam(required = false) String label,
	        @RequestParam(required = false) Integer memoNumber) {		
		Page<MemoResponse> memoPage = registrationBookService.getMemos(departmentId, (page - 1), PAGE_SIZE, sortBy, sortDir,
				new MemoFilter(recipientId, assigneeId, year, month, keyword, label, memoNumber));
		
		model.addAttribute("activePage", "admin/registration-book");
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("memoPage", memoPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", memoPage.getTotalPages());
        model.addAttribute("size", PAGE_SIZE);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir.name());
        model.addAttribute("reverseSortDir", sortDir.equals(Sort.Direction.ASC) ? Sort.Direction.DESC : Sort.Direction.ASC);
        
        int totalPages = memoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        
        model.addAttribute("recipientId", recipientId);
        model.addAttribute("assigneeId", assigneeId);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("keyword", keyword);
        model.addAttribute("label", label);
        model.addAttribute("memoNumber", memoNumber);
        
        var departmentResponse = departmentService.getDepartmentResponseById(departmentId);
        model.addAttribute("departmentResponse", departmentResponse);
        
        if (assigneeId == null) {
        	model.addAttribute("assignees", Collections.emptyList());
        } else {
            var userResponse = userService.getUserResponseById(assigneeId);
            model.addAttribute("assignees", List.of(userResponse));
        }
        
        if (recipientId == null) {
            model.addAttribute("recipients", Collections.emptyList());
        } else {
            var employeeResponse = employeeService.getEmployeeResponseById(recipientId);
            model.addAttribute("recipients", List.of(employeeResponse));
        }
        
        model.addAttribute("labels", label == null ? Collections.emptyList() : List.of(label));
        
		return "admin/registration-book/registration-book";
	}
	
}
