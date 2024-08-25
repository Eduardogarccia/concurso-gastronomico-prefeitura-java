package br.com.concurso.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.concurso.exceptions.EntityNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex){
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	}
}
