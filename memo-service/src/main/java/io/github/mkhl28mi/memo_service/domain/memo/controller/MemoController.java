package io.github.mkhl28mi.memo_service.domain.memo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.mkhl28mi.memo_service.domain.memo.dto.request.MemoRequest;
import io.github.mkhl28mi.memo_service.domain.memo.service.MemoService;

@Controller
@RequestMapping("/memos")
public class MemoController {
	
	@Autowired
	private MemoService memoService;
	
	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("activePage", "memos/create");
		model.addAttribute("memoRequest", new MemoRequest());
		return "memos/create-form";
	}
	
}
