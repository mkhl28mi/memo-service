package io.github.mkhl28mi.memo_service.domain.memo.log.controller;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.mkhl28mi.memo_service.domain.memo.log.service.MemoLogService;

@Controller
@RequestMapping("/memos/{id}/logs")
@PreAuthorize("isAuthenticated()")
public class MemoLogController {
	
	private final MemoLogService memoLogService;
	
	public MemoLogController(MemoLogService memoLogService) {
		super();
		this.memoLogService = memoLogService;
	}

	@GetMapping
	public String getLogs(@PathVariable UUID id, Model model) {
		model.addAttribute("logs", memoLogService.getMemoLogsById(id));
		
		return "memos/logs/logs";
	}

}
