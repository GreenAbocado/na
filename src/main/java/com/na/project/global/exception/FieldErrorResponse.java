package com.na.project.global.exception;

import org.springframework.validation.FieldError;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldErrorResponse {
	private final String name;
	private final String message;

	// @ModelAttribute 사용 시 바인딩 에러(타입 변환 실패 등)는 불필요한 메시지가 길어 변경
	public static FieldErrorResponse from(FieldError fieldError) {
		return new FieldErrorResponse(
			fieldError.getField(),
			fieldError.isBindingFailure() ? "잘못된 형식의 값입니다." : fieldError.getDefaultMessage()
		);
	}
}