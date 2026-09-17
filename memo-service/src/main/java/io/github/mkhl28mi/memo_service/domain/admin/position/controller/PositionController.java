package io.github.mkhl28mi.memo_service.domain.admin.position.controller;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
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

import io.github.mkhl28mi.memo_service.domain.admin.position.dto.request.PositionRequest;
import io.github.mkhl28mi.memo_service.domain.admin.position.service.PositionService;

@Controller
@RequestMapping("/admin/positions")
@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
public class PositionController {
	
	private static final String REDIRECT_POSITIONS = "redirect:/admin/positions";
	
	private final PositionService positionService;
	
	public PositionController(PositionService positionService) {
		super();
		this.positionService = positionService;
	}

	@GetMapping
	public String getPositions(@RequestParam(required = false) String search, Model model) {
		model.addAttribute("activePage", "admin/positions");
		model.addAttribute("positionRequest", new PositionRequest());
		model.addAttribute("positions", positionService.getPostions(search));
		
        return "admin/positions/positions";
	}
	
	@PostMapping
	public String addPosition(@ModelAttribute PositionRequest positionRequest) {
		positionService.addPosition(positionRequest);
		
		return REDIRECT_POSITIONS;
	}
	
	@GetMapping("/{id}")
	public String getPosition(@PathVariable UUID id, Model model) {
		model.addAttribute("activePage", "admin/positions");
		model.addAttribute("positionId", id);
		model.addAttribute("positionRequest", new PositionRequest(positionService.getPositionResponseById(id)));
		
		return "admin/positions/position";
	}
	
	@PutMapping("/{id}")
	public String updatePosition(@PathVariable UUID id, @ModelAttribute PositionRequest positionRequest) {
		positionService.updatePosition(id, positionRequest);
		
		return REDIRECT_POSITIONS;
	}
	
	@DeleteMapping("/{id}")
	public String deletePosition(@PathVariable UUID id) {
		positionService.deletePosition(id);
		
		return REDIRECT_POSITIONS;
	}
	
}
