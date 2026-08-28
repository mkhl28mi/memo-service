package io.github.mkhl28mi.memo_service.domain.memo.controller;

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
import io.github.mkhl28mi.memo_service.domain.memo.dto.request.MemoRequest;
import io.github.mkhl28mi.memo_service.domain.memo.dto.response.MemoResponse;
import io.github.mkhl28mi.memo_service.domain.memo.service.MemoService;
import io.github.mkhl28mi.memo_service.exception.BusinessException;


@Controller
@RequestMapping("/memos")
public class MemoController {
	
	private static final int PAGE_SIZE = 20;
	
	private final MemoService memoService;
	
	public MemoController(MemoService memoService) {
		this.memoService = memoService;
	}
	
	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("activePage", "memos/create");
		model.addAttribute("memoRequest", new MemoRequest());
		
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
	public String print(@PathVariable UUID id, Model model) {
		model.addAttribute("printTemplateData", memoService.getPrintTemplateData(id));
		
		return "memos/print/print";
	}
	
	@GetMapping("/registration-book")
	public String showRegistrationBook(@AuthenticationPrincipal CustomUserDetails userDetails, Model model,
			@RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "id") String sortBy,
	        @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
		Page<MemoResponse> memoPage = memoService.getMemos(userDetails.getUser(), (page - 1), PAGE_SIZE, sortBy, sortDir);
		
		model.addAttribute("activePage", "memos/registration-book");
		model.addAttribute("memoPage", memoPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", memoPage.getTotalPages());
        model.addAttribute("size", PAGE_SIZE);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir.name());
        
        int totalPages = memoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        
		return "memos/registration-book/registration-book";
	}
	
}
