package com.movie.rental.model;

public enum ExceptionMessage {
EMPTY("Rental Cart is Empty"),	
RENTAL_NOTFOUND ("No rental found with id: "),
MOVIE_NOTFOUND("No movie found with id: "),
NO_RECORD("No record found"),
DUPLICATE("Duplicate value exists"),
USER_NOTFOUND("No user found with id: ");
	private final String message;
	ExceptionMessage(String message)
	{
		this.message = message;
	}
	public String getMessage()
	{
		return this.message;
	}
}
