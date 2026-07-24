package io.github.mkhl28mi.memo_service.domain.application_setting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mkhl28mi.memo_service.domain.application_setting.entity.ApplicationSetting;

public interface ApplicationSettingRepository extends JpaRepository<ApplicationSetting, ApplicationSetting.Key> {
	
}
