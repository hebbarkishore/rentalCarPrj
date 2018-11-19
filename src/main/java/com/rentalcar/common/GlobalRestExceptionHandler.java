package com.rentalcar.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rentalcar.bean.ApplicationException;
import com.rentalcar.bean.ErrorResponseDto;

@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> exceptionHandler(Exception ex) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage("Please contact system administrator");
		if( ex instanceof ApplicationException ) {
			error.setErrorCode(HttpStatus.BAD_REQUEST.value());
			error.setMessage(ex.getMessage());
		}
		return new ResponseEntity<ErrorResponseDto>(error, HttpStatus.OK);
	}
}