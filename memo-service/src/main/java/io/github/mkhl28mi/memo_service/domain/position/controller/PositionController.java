package io.github.mkhl28mi.memo_service.domain.position.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.mkhl28mi.memo_service.domain.position.dto.request.PositionRequest;
import io.github.mkhl28mi.memo_service.domain.position.service.PositionService;

@Controller
@RequestMapping("/admin/employees/positions")
public class PositionController {
	
	@Autowired
	private PositionService positionService;
	
	@GetMapping
	public String getPositions(@RequestParam(required = false) String search, Model model) {
		model.addAttribute("activePage", "admin/employees/positions");
		model.addAttribute("positionRequest", new PositionRequest());
		model.addAttribute("positions", positionService.getPostions(search));
        return "admin/employees/positions/positions";
	}
	
	@PostMapping
	public String addPosition(@ModelAttribute("positionRequest") PositionRequest positionRequest) {
		positionService.addPosition(positionRequest);
		return "redirect:/admin/employees/positions";
	}
	
	@GetMapping("/{id}")
	public String getEmployeePosition(@PathVariable UUID id, Model model) {
		model.addAttribute("activePage", "admin/employees/positions");
		model.addAttribute("positionId", id);
		model.addAttribute("positionRequest", new PositionRequest(positionService.getPositionResponseById(id)));
		return "admin/employees/positions/position";
	}
	
	@PutMapping("/{id}")
	public String updateEmployeePosition(@PathVariable UUID id, @ModelAttribute("employeePositionRequest") PositionRequest employeePositionRequest) {
		positionService.updatePosition(id, employeePositionRequest);
		return "redirect:/admin/employees/positions";
	}
	
	@DeleteMapping("/{id}")
	public String deleteEmployeePosition(@PathVariable UUID id) {
		positionService.deletePosition(id);
		return "redirect:/admin/employees/positions";
	}
	
}
