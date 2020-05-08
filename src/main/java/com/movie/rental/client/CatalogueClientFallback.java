package com.movie.rental.client;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.movie.rental.model.Movie;
@Component
public class CatalogueClientFallback implements CatalogueClient {

	@Override
	public Optional<Movie> getMovieById(int id) {
		
		return null;
	}

}
