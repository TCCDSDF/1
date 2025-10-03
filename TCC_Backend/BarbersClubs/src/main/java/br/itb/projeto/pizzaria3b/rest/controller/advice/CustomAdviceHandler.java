package br.itb.projeto.pizzaria3b.rest.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.itb.projeto.pizzaria3b.rest.exception.ErrorResponse;
import br.itb.projeto.pizzaria3b.rest.exception.ResourceNotFoundException;

@ControllerAdvice
public class CustomAdviceHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlerResourceNotFoundException(ResourceNotFoundException e, WebRequest request){
		ErrorResponse errorResponse = new ErrorResponse(
			HttpStatus.NOT_FOUND.value(),
			"Not Found",
			e.getMessage(),
			request.getDescription(false)
		);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception e, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			"Internal Server Error",
			e.getMessage(),
			request.getDescription(false)
		);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
