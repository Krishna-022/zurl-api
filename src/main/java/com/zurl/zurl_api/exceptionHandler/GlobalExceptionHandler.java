package com.zurl.zurl_api.exceptionHandler;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.InvalidUrlException;
import com.zurl.zurl_api.dto.out.ErrorResponseOutDto;
import com.zurl.zurl_api.exception.ServiceUnavailableException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InvalidUrlException.class)
	public ResponseEntity<ErrorResponseOutDto> handleInvalidUrlException(InvalidUrlException invalidUrlException) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorResponseOutDto(List.of(invalidUrlException.getMessage())));
	}
	
	@ExceptionHandler(ServiceUnavailableException.class)
	public ResponseEntity<ErrorResponseOutDto> handleServiceUnavailableException(ServiceUnavailableException serviceUnavailableException) {
		return ResponseEntity
				.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(new ErrorResponseOutDto(List.of(serviceUnavailableException.getMessage())));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseOutDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
		List<FieldError> allErrors = methodArgumentNotValidException.getFieldErrors();
		List<String> messageList = new ArrayList<>();
				
		for (int i = 0; i < allErrors.size(); i++) {
			FieldError err = allErrors.get(i);
			if ("typeMismatch".equals(err.getCode()) || err.getCode().contains("typeMismatch")) {
				messageList.add("Invalid request format");
			} else {
				String message = err.getDefaultMessage();
				messageList.add(message);	
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseOutDto(messageList));
	}
}
