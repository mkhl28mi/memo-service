package io.github.mkhl28mi.memo_service.domain.memo.dto.response;

public record PrintTemplateDataResponse(String marginTop,
		String marginLeft,
		String marginRight,
		String marginBottom,
		String orientation,
		String paperSize,
		String companyName,
		MemoResponse memoResponse) {

}
