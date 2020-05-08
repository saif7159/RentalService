package com.movie.rental.client;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.movie.rental.model.User;
@Component
public class UserClientFallback implements UserClient {

	@Override
	public Optional<User> findById(int id){
		return null;
	}

}
