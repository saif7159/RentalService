package com.movie.rental.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.movie.rental.model.ErrorResponse;
import com.movie.rental.model.ExceptionMessage;

@ControllerAdvice
public class RentalExceptionController {
	@ExceptionHandler(value = RentalNotFoundException.class)
	public ResponseEntity<ErrorResponse> rentalNotFound(RentalNotFoundException rentalexception) {
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), rentalexception.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> validationError(DataIntegrityViolationException exception) {
		ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ExceptionMessage.DUPLICATE.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.CONFLICT);

	}

	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> userNotFound(UserNotFoundException exception) {
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = MovieNotFoundException.class)
	public ResponseEntity<ErrorResponse> movieNotFound(MovieNotFoundException exception) {
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = MovieUnavailableException.class)
	public ResponseEntity<ErrorResponse> movieOutOfStock(MovieUnavailableException exception)
	{
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),exception.getMessage());
		return new ResponseEntity<ErrorResponse>(error,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> invalidArgumentType(MethodArgumentTypeMismatchException exception) {
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = ServiceDownException.class)
	public ResponseEntity<ErrorResponse> serviceDown(ServiceDownException exception)
	{
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(),exception.getMessage());
		return new ResponseEntity<ErrorResponse>(error,HttpStatus.NOT_FOUND);
	}

}
