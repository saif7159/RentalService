package com.movie.rental.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.movie.rental.model.Movie;
import com.movie.rental.model.Rental;
import com.movie.rental.model.User;
import com.movie.rental.service.RentalService;

@RestController
@RefreshScope
public class RentalController {
	@Autowired
	private RentalService service;

	@PostMapping("/createrental")
	public Rental createRental(@RequestBody Rental rental) throws InterruptedException, ExecutionException {
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
		Date duedate = c.getTime();
		//log.debug(duedate.toString());
		newrental.setUserid(rental.getUserid());
		newrental.setMovieid(rental.getMovieid());
		newrental.setUsername(user.getName());

		newrental.setMoviename(movie.getMovie());
		newrental.setYear(movie.getYear());
		newrental.setDirector(movie.getDirector());
		newrental.setRentaldate(curr);
		newrental.setDuedate(duedate);
		newrental.setRent(movie.getRent());


		return service.createRental(newrental);
	}

	@GetMapping("/get/userid/{id}")
	public List<Rental> getAllById(@PathVariable int id) {
		return service.getRentalByUserId(id);
	}

	@GetMapping("/get/username/{username}")
	public List<Rental> getAllByUsername(@PathVariable String username) {
		return service.findByUsername(username);
	}

	@GetMapping("/get/moviename/{moviename}")
	public List<Rental> getAllByMoviename(@PathVariable String moviename) {
		return service.findByMoviename(moviename);
	}

	@DeleteMapping("/delete/userid/{userid}")
	public void deleteByUserid(@PathVariable int userid) {
		service.deleteByUserid(userid);
	}

	@DeleteMapping("/delete/id/{id}")
	public void deleteById(@PathVariable int id) {
		service.deleteById(id);
	}
}
