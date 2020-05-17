package com.movie.rental.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.management.ServiceNotFoundException;
import javax.naming.ServiceUnavailableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.movie.rental.client.CatalogueClient;
import com.movie.rental.client.UserClient;
import com.movie.rental.dao.RentalRepository;
import com.movie.rental.exception.MovieNotFoundException;
import com.movie.rental.exception.MovieUnavailableException;
import com.movie.rental.exception.RentalNotFoundException;
import com.movie.rental.exception.ServiceDownException;
import com.movie.rental.exception.UserNotFoundException;
import com.movie.rental.model.ExceptionMessage;
import com.movie.rental.model.Movie;
import com.movie.rental.model.Rental;
import com.movie.rental.model.User;

@Service
public class RentalServiceImpl implements RentalService {
	@Autowired
	private RentalRepository repo;
	@Autowired
	private CatalogueClient catclient;
	@Autowired
	private UserClient userclient;
	@Autowired
	private RestTemplate resttemplate;
	private static final Logger log = LoggerFactory.getLogger(RentalService.class);

	@Override
	public Rental createRental(Rental rent) {
		log.info("test log" + Thread.currentThread().getName());
		return repo.save(rent);
	}

	// Async Service calls try
	@Override
	@Async
	public CompletableFuture<User> findUser(int id) {
		log.info("Looking up for the user" + id + " " + Thread.currentThread().getName());

		Optional<User> u = userclient.findById(id);
		if(u.isEmpty()) throw new UserNotFoundException(ExceptionMessage.USER_NOTFOUND.getMessage()+id);
		if(u.get().getUserid()==0) throw new ServiceDownException(ExceptionMessage.USER_DOWN.getMessage());
		
		// User u = resttemplate.getForObject("http://localhost:8021/" + id,
		// User.class);
//		try {
//			Thread.sleep(2000L);
//			log.info("Finally Found User" + id);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		return CompletableFuture.completedFuture(u.get());
	}

	@Override
	@Async
	public CompletableFuture<Movie> findMovie(int id) {
		log.info("Looking for Movie" + id + " " + Thread.currentThread().getName());

		Optional<Movie> m = catclient.getMovieById(id);
		if(m.isEmpty()) throw new MovieNotFoundException(ExceptionMessage.MOVIE_NOTFOUND.getMessage()+id);
		if(m.get().getId()==0) throw new ServiceDownException(ExceptionMessage.MOVIE_DOWN.getMessage());
		if(!m.get().isAvailable()) throw new MovieUnavailableException("Movie is out of stock");
		// Movie m = resttemplate.getForObject("http://localhost:8022/getmovie/mid/" +
		// id, Movie.class);
		log.info("Finally Found Movie" + id);
		return CompletableFuture.completedFuture(m.get());
	}

	@Override
	@Cacheable(value = "rental",key = "#userid",unless="#result == null")
	public List<Rental> getRentalByUserId(Integer userid) {
		
		return repo.findByUserid(userid);
	}

	@Override
	@Cacheable(value = "rental",key = "#movie",unless="#result == null")
	public List<Rental> findByMoviename(String movie) {
		
		return repo.findByMoviename(movie);
	}

	@Override
	@Cacheable(value = "rental",key = "#user",unless="#result == null")
	public List<Rental> findByUsername(String user) {
		
		return repo.findByUsername(user);
	}

	@Override
	public void deleteByUserid(Integer userid) {
		repo.deleteAll(getRentalByUserId(userid));
	}

	@Override
	public void deleteById(Integer id) {
		repo.deleteById(id);
	}

	@Override
	public Optional<Rental> findById(Integer id) {
		return repo.findById(id);
	}

	

}
