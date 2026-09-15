package io.github.mkhl28mi.memo_service.domain.memo.comment.controller;

import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.mkhl28mi.memo_service.config.security.CustomUserDetails;
import io.github.mkhl28mi.memo_service.domain.memo.comment.service.MemoCommentService;

@Controller
@RequestMapping("/memos/{id}/comments")
public class MemoCommentController {
	
	private final MemoCommentService memoCommentService;
	
	public MemoCommentController(MemoCommentService memoCommentService) {
		super();
		this.memoCommentService = memoCommentService;
	}
	
	@GetMapping
	public String getComments(@PathVariable UUID id, Model model) {
		model.addAttribute("comments", memoCommentService.getMemoCommentsByMemoId(id));
		
		return "memos/comments/comments";
	}
	
	@PostMapping
	public String addComment(@PathVariable UUID id, @AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam String content) {
		memoCommentService.addComment(id, userDetails.getId(), content);
		
		return "redirect:/memos/approval";
	}
	
	@GetMapping("/create")
	public String showCreateForm(@PathVariable UUID id, Model model) {
		model.addAttribute("memoId", id);
		
		return "memos/comments/create-form";
	}

}
