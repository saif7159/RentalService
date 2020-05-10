package com.movie.rental.client;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.movie.rental.model.Movie;


@FeignClient(name = "movie-service",fallback = CatalogueClientFallback.class)
public interface CatalogueClient {	
@RequestMapping(value = "/getmovie/mid/{id}",method = RequestMethod.GET)
public Optional<Movie> getMovieById(@PathVariable int id);
}
