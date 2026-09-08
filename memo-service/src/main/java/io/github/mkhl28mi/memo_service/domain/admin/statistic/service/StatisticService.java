package io.github.mkhl28mi.memo_service.domain.admin.statistic.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.admin.department.dto.response.DepartmentResponse;
import io.github.mkhl28mi.memo_service.domain.admin.department.service.DepartmentService;
import io.github.mkhl28mi.memo_service.domain.admin.statistic.dto.response.BasicStatisticResponse;
import io.github.mkhl28mi.memo_service.domain.memo.service.MemoService;

@Service
@Transactional(readOnly = true)
public class StatisticService {
	
	private final DepartmentService departmentService;
	
	private final MemoService memoService;
	
	public StatisticService(DepartmentService departmentService, MemoService memoService) {
		super();
		this.departmentService = departmentService;
		this.memoService = memoService;
	}
	
	public List<BasicStatisticResponse> getBasicStatistics() {
		var list = new ArrayList<BasicStatisticResponse>();
		
		for (DepartmentResponse response : departmentService.getDepartments(null)) {
			long count = memoService.getCountByDepartment(response.id());
			
			long countOfMonth = memoService.getCountByDepartmentAndCurrentMonth(response.id());
			
			long countOfYear = memoService.getCountByDepartmentAndCurrentYear(response.id());
			
			list.add(new BasicStatisticResponse(response.name(), count, countOfMonth, countOfYear));
		}
		
		return list;
	}
	
}
