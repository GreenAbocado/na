package com.na.project.global.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// page는 0-based (Spring 기본값). 필요 시 1-based 변경 검토
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PageResponse<T> {
	private final List<T> contents;
	private final int currentPage;
	private final int pageSize;
	private final long totalElements;
	private final int totalPages;

	public static <T> PageResponse<T> from(Page<T> page) {
		return new PageResponse<>(
			page.getContent(),
			page.getNumber(),
			page.getSize(),
			page.getTotalElements(),
			page.getTotalPages()
		);
	}
}