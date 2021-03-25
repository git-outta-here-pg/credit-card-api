package com.publicis.sapient.credit.card.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.publicis.sapient.credit.card.api.exception.ApiError;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author poonamgupta
 *
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { ConstraintViolationException.class })
	protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {

		String error = "Provided input parameters have not met validation requirements";
		return new ResponseEntity<>(constructApiError(error), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	protected ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex, WebRequest request) {

		String error = "Provided credit card already exists in the system";
		return new ResponseEntity<>(constructApiError(error), HttpStatus.CONFLICT);
	}

	@Override
	@ExceptionHandler(ValidationException.class)
	public final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<String> errors = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors())
			errors.add(error.getField() + " - " + error.getDefaultMessage());
		ApiError apiError = new ApiError("Valdation Error!", errors);

		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String error = "Malformed JSON request";
		return new ResponseEntity<>(constructApiError(error), HttpStatus.BAD_REQUEST);
	}

	private ApiError constructApiError(String error) {
		List<String> errors = new ArrayList<>();
		errors.add(error);
		return new ApiError("Error!", errors);
	}

}
