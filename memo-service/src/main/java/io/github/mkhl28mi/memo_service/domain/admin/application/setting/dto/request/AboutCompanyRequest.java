package io.github.mkhl28mi.memo_service.domain.admin.application.setting.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AboutCompanyRequest(@NotBlank(message = "Name cannot be blank") String name) {

}
