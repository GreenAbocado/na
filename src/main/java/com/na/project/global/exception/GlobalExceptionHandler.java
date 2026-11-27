package com.na.project.global.exception;

import static com.na.project.global.exception.ErrorCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	// HTTP 요청 형식 예외 (Json 파싱 실패, 쿼리/경로변수 타입변환 실패, 필수 쿼리 누락)
	@ExceptionHandler({
		HttpMessageNotReadableException.class,
		MethodArgumentTypeMismatchException.class,
		MissingServletRequestParameterException.class})
	public ResponseEntity<ErrorResponse> handleHttpFormatException(HttpServletRequest request, Exception e) {
		return handleError(request, REQUEST_FORMAT_INVALID);
	}

	// 검증 - 객체 (ModelAttribute(바인딩 에러 포함), DTO)
	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(HttpServletRequest request, BindException e) {
		log.warn("[{} {}] {}", request.getMethod(), request.getRequestURI(), REQUEST_VALUE_INVALID.getCode());

		return ResponseEntity.status(REQUEST_VALUE_INVALID.getStatus())
			.body(ErrorResponse.of(REQUEST_VALUE_INVALID, e.getFieldErrors()));
	}

	// 쿼리, 경로변수 빈 검증 실패 예외
	@ExceptionHandler({
		HandlerMethodValidationException.class,
		ConstraintViolationException.class})
	public ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(HttpServletRequest request) {
		return handleError(request, REQUEST_VALUE_INVALID);
	}

	// 비즈니스 예외
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(HttpServletRequest request, CustomException e) {
		return handleError(request, e.getErrorCode());
	}

	// URL Not Found
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoResourceFoundException(HttpServletRequest request) {
		return handleError(request, REQUEST_URL_INVALID);
	}

	// HTTP 메서드 불일치
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpServletRequest request) {
		return handleError(request, REQUEST_METHOD_INVALID);
	}

	// 미디어 타입 불일치
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportException(HttpServletRequest request) {
		return handleError(request, REQUEST_MEDIA_INVALID);
	}

	// 서버 내부 오류 (잡지 못한 클라 오류도 포함)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllException(HttpServletRequest request, Exception e) {
		log.error("[{} {}] {}", request.getMethod(), request.getRequestURI(), SERVER_ERROR.getCode(), e);
		return ResponseEntity.status(SERVER_ERROR.getStatus())
			.body(ErrorResponse.of(SERVER_ERROR));
	}

	// 클라이언트 예외 (BindException 제외), 로깅 후 ErrorResponse 생성
	private ResponseEntity<ErrorResponse> handleError(HttpServletRequest request, ErrorCode errorCode) {
		log.warn("[{} {}] ErrorCode={}", request.getMethod(), request.getRequestURI(), errorCode.getCode());
		return ResponseEntity.status(errorCode.getStatus())
			.body(ErrorResponse.of(errorCode));
	}
}