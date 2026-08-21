package io.github.mkhl28mi.memo_service.domain.admin.application.setting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mkhl28mi.memo_service.domain.admin.application.setting.entity.ApplicationSetting;

public interface ApplicationSettingRepository extends JpaRepository<ApplicationSetting, ApplicationSetting.Key> {
	
}
