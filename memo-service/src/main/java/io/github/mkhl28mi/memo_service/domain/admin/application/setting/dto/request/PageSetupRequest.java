package io.github.mkhl28mi.memo_service.domain.admin.application.setting.dto.request;

import jakarta.validation.constraints.Pattern;

public record PageSetupRequest(@Pattern(regexp = "\\d+(\\.\\d+)?$", message = "Must be a valid decimal number") String marginTop, 
		@Pattern(regexp = "\\d+(\\.\\d+)?$", message = "Must be a valid decimal number") String marginBottom,
		@Pattern(regexp = "\\d+(\\.\\d+)?$", message = "Must be a valid decimal number") String marginLeft,
		@Pattern(regexp = "\\d+(\\.\\d+)?$", message = "Must be a valid decimal number") String marginRight,
		@Pattern(regexp = "portrait|landscape", message = "Orientation of paper must be portrait or landscape") String orientation,
		@Pattern(regexp = "A3|A4|A5", message = "Size of paper must be A3 or A4 or A5") String paperSize) {
}
