package io.github.mkhl28mi.memo_service.domain.admin.application.setting.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.admin.application.setting.dto.request.AboutCompanyRequest;
import io.github.mkhl28mi.memo_service.domain.admin.application.setting.dto.request.PageSetupRequest;
import io.github.mkhl28mi.memo_service.domain.admin.application.setting.entity.ApplicationSetting;
import io.github.mkhl28mi.memo_service.domain.admin.application.setting.entity.ApplicationSetting.Key;
import io.github.mkhl28mi.memo_service.domain.admin.application.setting.repository.ApplicationSettingRepository;

@Service
@Transactional(readOnly = true)
public class ApplicationSettingService {
	
	private final ApplicationSettingRepository applicationSettingRepository;
	
	public ApplicationSettingService(ApplicationSettingRepository applicationSettingRepository) {
		this.applicationSettingRepository = applicationSettingRepository;
	}

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
	
	public AboutCompanyRequest getAboutCompany() { 
		Optional<String> name = applicationSettingRepository.findById(Key.ABOUT_COMPANY_NAME)
				.map(ApplicationSetting::getValue);
		
		return new AboutCompanyRequest(name.orElse(""));
	}
	
	@Transactional
	public void updateAboutCompany(AboutCompanyRequest aboutCompanyRequest) {
		applicationSettingRepository.save(new ApplicationSetting(Key.ABOUT_COMPANY_NAME, aboutCompanyRequest.name()));
	}
	
}
