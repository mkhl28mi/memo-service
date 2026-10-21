package io.github.mkhl28mi.memo_service.domain.memo.comment.controller;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.mkhl28mi.memo_service.config.CustomUserDetails;
import io.github.mkhl28mi.memo_service.domain.memo.comment.dto.request.MemoCommentRequest;
import io.github.mkhl28mi.memo_service.domain.memo.comment.service.MemoCommentService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/memos/{id}/comments")
@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
public class MemoCommentController {
	
	private final MemoCommentService memoCommentService;
	
	public MemoCommentController(MemoCommentService memoCommentService) {
		super();
		this.memoCommentService = memoCommentService;
	}
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public String getComments(@PathVariable UUID id, Model model) {
		model.addAttribute("comments", memoCommentService.getMemoCommentsByMemoId(id));
		model.addAttribute("memoCommentRequest", new MemoCommentRequest());
		
		return "memos/comments/comments";
	}
	
	@PostMapping
	public String addComment(@PathVariable UUID id, @AuthenticationPrincipal CustomUserDetails userDetails, @Valid @ModelAttribute MemoCommentRequest memoCommentRequest, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("comments", memoCommentService.getMemoCommentsByMemoId(id));
			model.addAttribute("memoCommentRequest", memoCommentRequest);
			
			return "admin/users/assignments/assignments";
        }
		
		memoCommentService.addComment(id, userDetails.getId(), memoCommentRequest);
		
		return "redirect:/memos/approval";
	}
	
	@GetMapping("/create")
	public String showCreateForm(@PathVariable UUID id, Model model) {
		model.addAttribute("memoId", id);
		
		return "memos/comments/create-form";
	}

}
