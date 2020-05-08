package com.movie.rental.client;

import java.util.Optional;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.movie.rental.model.User;

@FeignClient(name = "user-service", fallback = UserClientFallback.class)
public interface UserClient {
	@LoadBalanced
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Optional<User> findById(@PathVariable int id);
}
