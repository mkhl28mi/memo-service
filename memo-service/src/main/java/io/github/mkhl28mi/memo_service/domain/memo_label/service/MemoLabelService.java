package io.github.mkhl28mi.memo_service.domain.memo_label.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.memo_label.entity.MemoLabel;
import io.github.mkhl28mi.memo_service.domain.memo_label.repository.MemoLabelRepository;

@Service
@Transactional(readOnly = true)
public class MemoLabelService {
	
	@Autowired
	private MemoLabelRepository memoLabelRepository;
	
	public List<String> getDistinctLabelsAsString(String search) throws IllegalArgumentException {
		if (search == null) { throw new IllegalArgumentException("search cannot be null."); }
		return memoLabelRepository.searchDistinctByName(search).stream()
				.map(MemoLabel::getName)
				.toList();
	}
	
	
	
}
