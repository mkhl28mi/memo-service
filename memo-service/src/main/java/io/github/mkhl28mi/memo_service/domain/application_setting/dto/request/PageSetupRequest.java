package io.github.mkhl28mi.memo_service.domain.application_setting.dto.request;

import jakarta.validation.constraints.Pattern;

public record PageSetupRequest(@Pattern(regexp = "\\d+(\\.\\d+)?$", message = "Must be a valid decimal number") String marginTop, 
		@Pattern(regexp = "\\d+(\\.\\d+)?$", message = "Must be a valid decimal number") String marginBottom,
		@Pattern(regexp = "\\d+(\\.\\d+)?$", message = "Must be a valid decimal number") String marginLeft,
		@Pattern(regexp = "\\d+(\\.\\d+)?$", message = "Must be a valid decimal number") String  marginRight,
		String orientation,
		String paperSize) {
}
