package io.github.mkhl28mi.memo_service.domain.memo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mkhl28mi.memo_service.domain.memo.dto.request.MemoRequest;
import io.github.mkhl28mi.memo_service.domain.memo.entity.Memo;
import io.github.mkhl28mi.memo_service.domain.memo.repository.MemoRepository;

@Service
public class MemoService {
	
	@Autowired
	private MemoRepository memoRepository;
	
	
	public void addMemo(MemoRequest memoRequest) {
		
		
		
		
		Memo memo = new Memo(null, null, null, null, null, 0, 0);
		
		
	}
	

}
