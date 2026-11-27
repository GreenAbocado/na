package com.na.project.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

	// 공통
	REQUEST_FORMAT_INVALID(HttpStatus.BAD_REQUEST, "G001", "요청 형식이 잘못되었습니다."),
	REQUEST_VALUE_INVALID(HttpStatus.BAD_REQUEST, "G002", "요청 값이 잘못되었습니다."),
	REQUEST_URL_INVALID(HttpStatus.NOT_FOUND, "G003", "URL 경로가 잘못되었습니다"),
	REQUEST_METHOD_INVALID(HttpStatus.METHOD_NOT_ALLOWED, "G004", "HTTP 메서드가 잘못되었습니다."),
	REQUEST_MEDIA_INVALID(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "G005", "지원하지 않는 미디어 타입입니다."),

	SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G500", "서버 내부 오류입니다.");

	// 회원

	// 상품

	// 주문

	// ...

	private final HttpStatus status;

	// 에러 식별 코드. 도메인 접두사 + 번호 (예: 상품 P001, 회원 M001)
	private final String code;

	private final String message;
}