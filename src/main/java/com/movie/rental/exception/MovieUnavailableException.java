package com.movie.rental.exception;

public class MovieUnavailableException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public MovieUnavailableException(String message) {
		super(message);
	}
	
}
