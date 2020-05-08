package com.movie.rental.client;

import java.util.Optional;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.movie.rental.model.Movie;


@FeignClient(name = "movie-service",fallback = CatalogueClientFallback.class)
public interface CatalogueClient {
@LoadBalanced
@GetMapping("/getmovie/mid/{id}")
public Optional<Movie> getMovieById(@PathVariable int id);
}
