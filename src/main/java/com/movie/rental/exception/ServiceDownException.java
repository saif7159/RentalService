package com.movie.rental.exception;

public class ServiceDownException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceDownException(String message) {
		super(message);
	}

}
