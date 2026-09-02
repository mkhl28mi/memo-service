package io.github.mkhl28mi.memo_service.domain.admin.registration_book.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import io.github.mkhl28mi.memo_service.domain.memo.dto.request.MemoFilter;
import io.github.mkhl28mi.memo_service.domain.memo.dto.response.MemoResponse;
import io.github.mkhl28mi.memo_service.domain.memo.repository.specifications.MemoSpecifications;
import io.github.mkhl28mi.memo_service.domain.memo.service.MemoService;

@Service
public class RegistrationBookService {
	
	private final MemoService memoService;
	
	public RegistrationBookService(MemoService memoService) {
		super();
		this.memoService = memoService;
	}
	
	public Page<MemoResponse> getMemos(UUID departmentId, int page, int size, String sortBy, Sort.Direction direction, MemoFilter memoFilter) {
		Sort.Direction sortDirection = (direction != null) ? direction : Sort.Direction.ASC;
		
		String safeSortBy = switch (sortBy != null ? sortBy : "") {
		case "status", "sequenceNumber", "assignee.user.fullName", "createdAt" -> sortBy;
		default -> "id";
		};
		
		Sort.Order order = new Sort.Order(sortDirection, safeSortBy)
		        .nullsLast();
		
        Pageable pageable = PageRequest.of(
        		Math.max(page, 0), 
        		size, 
        		Sort.by(order));
              
       var specification = MemoSpecifications.filterMemos(departmentId, 
    		   memoFilter.memoNumber(), 
    		   memoFilter.assigneeId(), 
    		   memoFilter.keyword(), 
    		   memoFilter.year(), 
    		   memoFilter.month(), 
    		   memoFilter.label(), 
    		   memoFilter.recipientId());
       
        return memoService.getMemos(specification, pageable);
    }

}
