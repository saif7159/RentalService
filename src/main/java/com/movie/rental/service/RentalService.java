package com.movie.rental.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.movie.rental.model.Movie;
import com.movie.rental.model.Rental;
import com.movie.rental.model.User;

public interface RentalService {
	public Rental createRental(Rental rent);

	public List<Rental> getRentalByUserId(Integer userid);

	public List<Rental> findByMoviename(String movie);

	public List<Rental> findByUsername(String user);
	
	public Optional<Rental> findById(Integer id);

	public void deleteByUserid(Integer userid);

	public void deleteById(Integer id);

	public CompletableFuture<User> findUser(int id);

	public CompletableFuture<Movie> findMovie(int id);
}
