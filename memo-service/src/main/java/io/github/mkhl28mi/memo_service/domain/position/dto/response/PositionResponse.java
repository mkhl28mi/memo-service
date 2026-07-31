package io.github.mkhl28mi.memo_service.domain.position.dto.response;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.position.entity.Position;

public record PositionResponse(UUID id, 
		String name, 
		String targetName, 
		int placementOrder,
		boolean enabled,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {
	
	public PositionResponse(Position employeePosition) {
		this(employeePosition.getId(),
				employeePosition.getName(),
				employeePosition.getTargetName(),
				employeePosition.getPlacementOrder(),
				employeePosition.isEnabled(),
				employeePosition.getCreatedAt(),
				employeePosition.getUpdatedAt());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PositionResponse other = (PositionResponse) obj;
		return Objects.equals(id, other.id);
	}
	
}
