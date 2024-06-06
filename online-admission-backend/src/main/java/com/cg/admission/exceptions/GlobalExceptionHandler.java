package com.cg.admission.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest webRequest) {
		ErrorResponse details = new ErrorResponse(LocalDateTime.now(), exception.getMessage(),
				webRequest.getDescription(false));

		return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceAlreadyPresentException.class)
	public ResponseEntity<ErrorResponse> handleObjectAlreadyPresentException(ResourceAlreadyPresentException exception,
			WebRequest webRequest) {
		ErrorResponse details = new ErrorResponse(LocalDateTime.now(), exception.getMessage(),
				webRequest.getDescription(false));

		return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception, WebRequest webRequest) {
//        Map<String, String> errors = new HashMap<>();
//        exception.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
		ErrorResponse details = new ErrorResponse(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }	
}
