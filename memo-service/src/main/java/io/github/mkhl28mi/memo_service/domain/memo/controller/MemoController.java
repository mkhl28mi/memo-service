package io.github.mkhl28mi.memo_service.domain.memo.controller;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.mkhl28mi.memo_service.config.security.CustomUserDetails;
import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.service.EmployeeAssignmentService;
import io.github.mkhl28mi.memo_service.domain.admin.employee.service.EmployeeService;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.service.UserAssignmentService;
import io.github.mkhl28mi.memo_service.domain.admin.user.service.UserService;
import io.github.mkhl28mi.memo_service.domain.memo.dto.request.MemoRequest;
import io.github.mkhl28mi.memo_service.domain.memo.dto.request.MemoFilter;
import io.github.mkhl28mi.memo_service.domain.memo.dto.response.MemoResponse;
import io.github.mkhl28mi.memo_service.domain.memo.service.MemoService;
import io.github.mkhl28mi.memo_service.exception.BusinessException;


@Controller
@RequestMapping("/memos")
public class MemoController {
	
	private static final int PAGE_SIZE = 20;
	
	private final MemoService memoService;
	
	private final UserService userService;
	
	private final EmployeeService employeeService;
	
	private final UserAssignmentService userAssignmentService;
	
	private final EmployeeAssignmentService employeeAssignmentService;
	
	public MemoController(MemoService memoService, UserService userService, EmployeeService employeeService, UserAssignmentService userAssignmentService, EmployeeAssignmentService employeeAssignmentService) {
		super();
		this.memoService = memoService;
		this.userService = userService;
		this.employeeService = employeeService;
		this.userAssignmentService = userAssignmentService;
		this.employeeAssignmentService = employeeAssignmentService;
	}

	@GetMapping("/create")
	public String showCreateForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
		var currentUserAssignmentResponse = userAssignmentService.getCurrentUserAssignmentResponseByUserId(userDetails.getId());

		var signer = employeeAssignmentService.getSigner(currentUserAssignmentResponse.departmentUnitResponse().departmentResponse().id());
		
		model.addAttribute("activePage", "memos/create");
		model.addAttribute("memoRequest", new MemoRequest());
		model.addAttribute("signers", List.of(signer));
		model.addAttribute("signerIds", List.of(signer.id()));
		
		return "memos/create-form";
	}
	
	@PostMapping
	public String createMemo(@AuthenticationPrincipal CustomUserDetails userDetails, @ModelAttribute MemoRequest memoRequest) throws BusinessException {
		UUID memoId = memoService.createMemo(userDetails.getUser(), memoRequest);
		
		return String.format("redirect:/memos/%s", memoId);
	}
	
	@GetMapping("/{id}")
	public String getMemoById(@PathVariable UUID id, Model model) {
		var memoResponse = memoService.getMemoById(id);
		
		model.addAttribute("activePage", "memos/create");
		model.addAttribute("memoId", id);
		model.addAttribute("memoRequest", new MemoRequest(memoResponse));
		model.addAttribute("recipients", memoResponse.recipients());
		model.addAttribute("copyRecipients", memoResponse.copyRecipients());
		model.addAttribute("signers", memoResponse.signers());
		model.addAttribute("approvers", memoResponse.approvers());
		model.addAttribute("assignees", List.of(memoResponse.assignee()));
		model.addAttribute("labels", memoResponse.labels());
		
		return "memos/update-form";
	}
	
	@PutMapping("/{id}")
	public String updateMemo(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID id, @ModelAttribute MemoRequest memoRequest) throws BusinessException {
		memoService.updateMemo(userDetails.getUser(), id, memoRequest);
		
		return String.format("redirect:/memos/%s", id);
	}
	
	@GetMapping("/create-based-on/{id}")
	public String showCreateBasedOnForm(@PathVariable UUID id, Model model) {
		var memoResponse = memoService.getMemoById(id);
		
		model.addAttribute("activePage", "memos/create");
		model.addAttribute("memoRequest", new MemoRequest(memoResponse));
		model.addAttribute("recipients", memoResponse.recipients());
		model.addAttribute("copyRecipients", memoResponse.copyRecipients());
		model.addAttribute("signers", memoResponse.signers());
		model.addAttribute("approvers", memoResponse.approvers());
		model.addAttribute("assignees", List.of(memoResponse.assignee()));
		model.addAttribute("labels", memoResponse.labels());
		
		return "memos/create-based-on-form";
	}
	
	@GetMapping("/print/{id}")
	public String print(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID id, Model model) throws BusinessException {
		model.addAttribute("printTemplateData", memoService.getPrintTemplateData(userDetails.getUser(), id));
		
		return "memos/print/print";
	}
	
	@GetMapping("/registration-book")
	public String showRegistrationBook(@AuthenticationPrincipal CustomUserDetails userDetails, Model model,
			@RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "createdAt") String sortBy,
	        @RequestParam(defaultValue = "DESC") Sort.Direction sortDir,
	        @RequestParam(required = false) UUID recipientId,
	        @RequestParam(required = false) UUID assigneeId,
	        @RequestParam(required = false) Integer year,
	        @RequestParam(required = false) Integer month,
	        @RequestParam(required = false) String keyword,
	        @RequestParam(required = false) String label,
	        @RequestParam(required = false) Integer memoNumber) {
		Page<MemoResponse> memoPage = memoService.getMemos(userDetails.getUser(), (page - 1), PAGE_SIZE, sortBy, sortDir,
				new MemoFilter(recipientId, assigneeId, year, month, keyword, label, memoNumber));
		
		model.addAttribute("activePage", "memos/registration-book");
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
        
		var currentUserAssignment = userAssignmentService.getCurrentUserAssignmentResponseByUserId(userDetails.getUser().getId());
        model.addAttribute("departmentCode", currentUserAssignment.departmentUnitResponse().departmentResponse().code());
        
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
        
		return "memos/registration-book/registration-book";
	}
	
}
