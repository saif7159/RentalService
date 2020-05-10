package com.movie.rental.exception;

public class RentalNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RentalNotFoundException(String message) {
		super(message);
	}
	
}
