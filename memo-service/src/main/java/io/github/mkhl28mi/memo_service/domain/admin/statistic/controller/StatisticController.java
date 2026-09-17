package io.github.mkhl28mi.memo_service.domain.admin.statistic.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.mkhl28mi.memo_service.domain.admin.statistic.service.StatisticService;

@Controller
@RequestMapping("/admin/statistics")
@PreAuthorize("hasAnyRole('ADMIN')")
public class StatisticController {
	
	private final StatisticService statisticService;
	
	public StatisticController(StatisticService statisticService) {
		super();
		this.statisticService = statisticService;
	}
	
	@GetMapping
	public String getBasicStatistics(Model model) {
		model.addAttribute("activePage", "admin/statistics");
		model.addAttribute("basicStatistics", statisticService.getBasicStatistics());
		
		return "admin/statistics/statistics";
	}

}
