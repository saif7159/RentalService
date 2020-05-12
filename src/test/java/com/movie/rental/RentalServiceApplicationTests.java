package com.movie.rental;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.movie.rental.controller.RentalController;

@SpringBootTest
class RentalServiceApplicationTests {
	@Autowired
	private RentalController controller;
	@Test
	void contextLoads() {
	assertThat(controller).isNotNull();
	}

}
