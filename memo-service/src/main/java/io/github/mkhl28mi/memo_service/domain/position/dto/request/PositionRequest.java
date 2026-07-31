package io.github.mkhl28mi.memo_service.domain.position.dto.request;

import io.github.mkhl28mi.memo_service.domain.position.dto.response.PositionResponse;

public record PositionRequest(String name, String targetName, int placementOrder, boolean enabled) {
	
	public PositionRequest() {
		this("", "", 0, true);
	}
	
	public PositionRequest(PositionResponse employeePositionResponse) {
		this(employeePositionResponse.name(), employeePositionResponse.targetName(), employeePositionResponse.placementOrder(), employeePositionResponse.enabled());
	}
	
}
