package com.movie.rental.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.movie.rental.exception.RentalNotFoundException;
import com.movie.rental.model.ExceptionMessage;
import com.movie.rental.model.Movie;
import com.movie.rental.model.Rental;
import com.movie.rental.model.User;
import com.movie.rental.service.RentalService;

@RestController
@RefreshScope
public class RentalController {
	@Autowired
	private RentalService service;
	@Autowired
	private CacheManager manager;

	@PostMapping("/createrental")
	public Rental createRental(@RequestBody @Valid Rental rental) throws InterruptedException, ExecutionException {
		CompletableFuture<User> u = service.findUser(rental.getUserid());
		CompletableFuture<Movie> m = service.findMovie(rental.getMovieid());
		CompletableFuture.allOf(u,m).join();
		
		User user = u.get();
		Movie movie = m.get();
		
		
		Rental newrental = new Rental();
		Date curr = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(curr);
		c.add(Calendar.DATE, 7); // Setting return date after 7 days
		Date due = c.getTime();
		String currentdate = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(curr);
		String duedate = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(due);
		//log.debug(duedate.toString());
		newrental.setUserid(rental.getUserid());
		newrental.setMovieid(rental.getMovieid());
		newrental.setUsername(user.getName());

		newrental.setMoviename(movie.getMovie());
		newrental.setYear(movie.getYear());
		newrental.setDirector(movie.getDirector());
		newrental.setRentaldate(currentdate);
		newrental.setDuedate(duedate);
		newrental.setRent(movie.getRent());
		
		manager.getCache("rental").clear();
		return service.createRental(newrental);
	}

	@GetMapping("/get/userid/{id}")
	public List<Rental> getAllById(@PathVariable int id) {
		List<Rental> rentallist = service.getRentalByUserId(id);
		if(rentallist.isEmpty()) throw new RentalNotFoundException(ExceptionMessage.EMPTY.getMessage());
		return rentallist;
	}

	@GetMapping("/get/username/{username}")
	public List<Rental> getAllByUsername(@PathVariable String username) {
		List<Rental> rentallist = service.findByUsername(username);
		if(rentallist.isEmpty()) throw new RentalNotFoundException(ExceptionMessage.EMPTY.getMessage());
		return rentallist;
	}
	@GetMapping("/get/id/{id}")
	public Optional<Rental> getById(@PathVariable int id)
	{
		Optional<Rental> rental = service.findById(id);
		if(rental.isEmpty()) throw new RentalNotFoundException(ExceptionMessage.RENTAL_NOTFOUND.getMessage()+id);
		return rental;
	}

	@GetMapping("/get/moviename/{moviename}")
	public List<Rental> getAllByMoviename(@PathVariable String moviename) {
		List<Rental> rentallist = service.findByMoviename(moviename);
		if(rentallist.isEmpty()) throw new RentalNotFoundException(ExceptionMessage.NO_RECORD.getMessage());
		return rentallist;
	}

	@DeleteMapping("/delete/userid/{userid}")
	public void deleteByUserid(@PathVariable int userid) {
		if(service.getRentalByUserId(userid).isEmpty()) throw new RentalNotFoundException(ExceptionMessage.NO_RECORD.getMessage());
		manager.getCache("rental").clear();
		service.deleteByUserid(userid);
	}

	@DeleteMapping("/delete/id/{id}")
	public void deleteById(@PathVariable int id) {
		if(service.findById(id).isEmpty()) throw new RentalNotFoundException(ExceptionMessage.NO_RECORD.getMessage());
		manager.getCache("rental").clear();
		service.deleteById(id);
	}
}
