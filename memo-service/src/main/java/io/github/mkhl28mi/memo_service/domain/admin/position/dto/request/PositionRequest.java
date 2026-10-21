package io.github.mkhl28mi.memo_service.domain.admin.position.dto.request;

import io.github.mkhl28mi.memo_service.domain.admin.position.dto.response.PositionResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record PositionRequest(
		@NotNull(message = "Name cannot be null") @Size(min = 1, max = 400, message = "Name must be between 1 and 400 characters") String name, 
		@NotNull(message = "Target name cannot be null") @Size(min = 1, max = 400, message = "Target name must be between 1 and 400 characters")String targetName, 
		@PositiveOrZero(message = "Order cannot be less than zero") int placementOrder, 
		boolean enabled) {
	
	public PositionRequest() {
		this("", "", 0, true);
	}
	
	public PositionRequest(PositionResponse employeePositionResponse) {
		this(employeePositionResponse.name(), employeePositionResponse.targetName(), employeePositionResponse.placementOrder(), employeePositionResponse.enabled());
	}
	
}
