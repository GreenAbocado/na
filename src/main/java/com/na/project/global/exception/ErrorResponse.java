package com.na.project.global.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
public class ErrorResponse {
	private final String code;
	private final String message;
	private final LocalDateTime timestamp;

	// DTO, ModelAttribute 검증 예외 시에만 포함
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final List<FieldErrorResponse> fieldErrors;

	private ErrorResponse(ErrorCode errorCode, List<FieldErrorResponse> fieldErrors) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.timestamp = LocalDateTime.now();
		this.fieldErrors = fieldErrors;
	}

	// DTO, ModelAttribute 빈 밸리데이션 예외 응답
	public static ErrorResponse of(ErrorCode errorCode, List<FieldError> fieldErrors) {

		return new ErrorResponse(
			errorCode,
			fieldErrors.stream().map(FieldErrorResponse::from).toList()
		);
	}

	// DTO, ModelAttribute 빈 밸리데이션 예외를 제외한 모든 응답
	public static ErrorResponse of(ErrorCode errorCode) {
		return new ErrorResponse(errorCode, null);
	}
}