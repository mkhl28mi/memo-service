package io.github.mkhl28mi.memo_service.domain.application_setting.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.application_setting.dto.request.PageSetupRequest;
import io.github.mkhl28mi.memo_service.domain.application_setting.entity.ApplicationSetting;
import io.github.mkhl28mi.memo_service.domain.application_setting.entity.ApplicationSetting.Key;
import io.github.mkhl28mi.memo_service.domain.application_setting.repository.ApplicationSettingRepository;

@Service
@Transactional(readOnly = true)
public class ApplicationSettingService {
	
	@Autowired
	private ApplicationSettingRepository applicationSettingRepository;
	
	public PageSetupRequest getPageSetup() {
		Optional<String> marginTop = applicationSettingRepository.findById(Key.PAGE_MARGIN_TOP)
				.map(ApplicationSetting::getValue);
		Optional<String> marginBottom = applicationSettingRepository.findById(Key.PAGE_MARGIN_BOTTOM)
				.map(ApplicationSetting::getValue);
		Optional<String> marginLeft = applicationSettingRepository.findById(Key.PAGE_MARGIN_LEFT)
				.map(ApplicationSetting::getValue);
		Optional<String> marginRight = applicationSettingRepository.findById(Key.PAGE_MARGIN_RIGHT)
				.map(ApplicationSetting::getValue);
		Optional<String> orientation = applicationSettingRepository.findById(Key.PAGE_ORIENTATION)
				.map(ApplicationSetting::getValue);
		Optional<String> size = applicationSettingRepository.findById(Key.PAPER_SIZE)
				.map(ApplicationSetting::getValue);
		return new PageSetupRequest(marginTop.orElse(""), 
				marginBottom.orElse(""), 
				marginLeft.orElse(""), 
				marginRight.orElse(""), 
				orientation.orElse(""), 
				size.orElse(""));
	}
	
	@Transactional
	public void updatePageSetup(PageSetupRequest pageSetupRequest) {
		applicationSettingRepository.save(new ApplicationSetting(Key.PAGE_MARGIN_TOP, pageSetupRequest.marginTop()));
		applicationSettingRepository.save(new ApplicationSetting(Key.PAGE_MARGIN_BOTTOM, pageSetupRequest.marginBottom()));
		applicationSettingRepository.save(new ApplicationSetting(Key.PAGE_MARGIN_LEFT, pageSetupRequest.marginLeft()));
		applicationSettingRepository.save(new ApplicationSetting(Key.PAGE_MARGIN_RIGHT, pageSetupRequest.marginRight()));
		applicationSettingRepository.save(new ApplicationSetting(Key.PAGE_ORIENTATION, pageSetupRequest.orientation()));
		applicationSettingRepository.save(new ApplicationSetting(Key.PAPER_SIZE, pageSetupRequest.paperSize()));
	}
	
}
