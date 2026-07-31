package io.github.mkhl28mi.memo_service.domain.position.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.position.dto.request.PositionRequest;
import io.github.mkhl28mi.memo_service.domain.position.dto.response.PositionResponse;
import io.github.mkhl28mi.memo_service.domain.position.entity.Position;
import io.github.mkhl28mi.memo_service.domain.position.repository.PositionRepository;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class PositionService {
	
	@Autowired
	private PositionRepository positionRepository;
	
	public List<PositionResponse> getPostions(String search) {
    	if (search == null || search.trim().isEmpty()) {
    		return mapToPositionResponse(positionRepository.findAll()); 
    	} else {
    		return mapToPositionResponse(positionRepository.searchByNameOrTargetName(search.trim()));
    	}
	}
	
	public List<PositionResponse> getEnabledPostions(String search) throws IllegalArgumentException {
		if (search == null) { throw new IllegalArgumentException("Search cannot be null."); }
		
    	return mapToPositionResponse(positionRepository.searchEnabledByNameOrTargetName(search.trim()));
	}
	
	public Position getPositionById(UUID id) {
		return positionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + id));
	}
	
	public Optional<Position> getPositionByName(String name) {
		return positionRepository.findByName(name);
	}
	
	public PositionResponse getPositionResponseById(UUID id) {
		Position position = positionRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + id));
		return new PositionResponse(position);
	}
	
	@Transactional
	public PositionResponse addPosition(PositionRequest positionRequest) {
		return new PositionResponse(positionRepository.save(new Position(positionRequest.name(), 
				positionRequest.targetName(), 
				positionRequest.placementOrder(),
				positionRequest.enabled())));
	}
	
	@Transactional
	public PositionResponse updatePosition(UUID id, PositionRequest positionRequest) {
		Position position = positionRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + id));
		position.setName(positionRequest.name());
		position.setTargetName(positionRequest.targetName());
		position.setPlacementOrder(positionRequest.placementOrder());
		position.setEnabled(positionRequest.enabled());
		return new PositionResponse(positionRepository.save(position));
	}
	
	@Transactional
	public void deletePosition(UUID id) {
		positionRepository.deleteById(id);
	}
	
    private static List<PositionResponse> mapToPositionResponse(List<Position> positions) {
    	return positions.stream().map(PositionResponse::new).toList();
    }

}
