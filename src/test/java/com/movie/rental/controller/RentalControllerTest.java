package com.movie.rental.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.movie.rental.model.Movie;
import com.movie.rental.model.Rental;
import com.movie.rental.model.User;
import com.movie.rental.service.RentalService;

import net.minidev.json.writer.CompessorMapper;
@SpringBootTest
@AutoConfigureMockMvc
class RentalControllerTest {
	@MockBean
	private RentalService service;
	@Autowired
	private MockMvc mockMvc;
	@Test
	void testFindById() throws Exception {
		Optional<Rental> rental = Optional.of(new Rental(1, 1, 1, "saif", "BDE", 2014 , "OPE", "11-05-2020 17:41", "18-05-2020 17:41", 120.5F));
		when(service.findById(Mockito.anyInt())).thenReturn(rental);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/get/id/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int status = result.getResponse().getStatus();
		String expected = "{\"id\":1,\"userid\":1,\"movieid\":1,\"username\":\"saif\",\"moviename\":\"BDE\",\"year\":2014,\"director\":\"OPE\",\"rentaldate\":\"11-05-2020 17:41\",\"duedate\":\"18-05-2020 17:41\",\"rent\":120.5}";
		System.out.println("IM THE TESTER: " +result.getResponse().getContentAsString());
		assertEquals(200, status);
		//JSONAssert.assertEquals(expected, actual, strict);
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	@Test
	void testFindByUserId() throws Exception {
		when(service.getRentalByUserId(Mockito.anyInt())).thenReturn(Stream.of(
				new Rental(1, 1, 1, "saif", "BDE", 2014, "OPE", "11-05-2020 17:41", "18-05-2020 17:41", 120.5F), 
				new Rental(3, 1, 2, "saif", "AAA", 2014, "ZZZ", "11-05-2020 21:10", "18-05-2020 21:10", 120.5F)).collect(Collectors.toList()));
		mockMvc.perform(MockMvcRequestBuilders.get("/get/userid/1").accept(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(status().isOk()).andExpect(jsonPath("$",hasSize(2)))
		.andExpect(jsonPath("$[0].id", is(1)))
		.andExpect(jsonPath("$[0].userid", is(1)))
		.andExpect(jsonPath("$[0].movieid", is(1)))
		.andExpect(jsonPath("$[0].username", is("saif")))
		.andExpect(jsonPath("$[0].moviename", is("BDE")))
		.andExpect(jsonPath("$[1].id", is(3)))
		.andExpect(jsonPath("$[1].userid", is(1)))
		.andExpect(jsonPath("$[1].movieid", is(2)))
		.andExpect(jsonPath("$[1].username", is("saif")))
		.andExpect(jsonPath("$[1].moviename", is("AAA")));
	}
	@Test
	void testFindByMovieName() throws Exception{
		List<Rental> rental = Arrays.asList(new Rental(1, 1, 1, "saif", "BDE", 2014, "OPE", "11-05-2020 17:41", "18-05-2020 17:41", 120.5F),
											new Rental(2, 2, 1, "manu", "BDE", 2014, "OPE", "11-05-2020 17:41", "18-05-2020 17:41", 120.5F));
		when(service.findByMoviename(Mockito.anyString())).thenReturn(rental);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/get/moviename/BDE").accept(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(status().isOk()).andExpect(jsonPath("$",hasSize(2)))
		.andExpect(jsonPath("$[0].id", is(1)))
		.andExpect(jsonPath("$[0].userid", is(1)))
		.andExpect(jsonPath("$[0].movieid", is(1)))
		.andExpect(jsonPath("$[0].username", is("saif")))
		.andExpect(jsonPath("$[0].moviename", is("BDE")))
		.andExpect(jsonPath("$[1].id", is(2)))
		.andExpect(jsonPath("$[1].userid", is(2)))
		.andExpect(jsonPath("$[1].movieid", is(1)))
		.andExpect(jsonPath("$[1].username", is("manu")))
		.andExpect(jsonPath("$[1].moviename", is("BDE")));
	}
	@Test
	void testFindByUserName() throws Exception{
		List<Rental> rental = Arrays.asList(new Rental(1, 1, 1, "saif", "BDE", 2014, "OPE", "11-05-2020 17:41", "18-05-2020 17:41", 120.5F),
											new Rental(3, 1, 2, "saif", "AAA", 2014, "ZZZ", "11-05-2020 21:10", "18-05-2020 21:10", 120.5F));
		when(service.findByMoviename(Mockito.anyString())).thenReturn(rental);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/get/moviename/BDE").accept(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(status().isOk()).andExpect(jsonPath("$",hasSize(2)))
		.andExpect(jsonPath("$[0].id", is(1)))
		.andExpect(jsonPath("$[0].userid", is(1)))
		.andExpect(jsonPath("$[0].movieid", is(1)))
		.andExpect(jsonPath("$[0].username", is("saif")))
		.andExpect(jsonPath("$[0].moviename", is("BDE")))
		.andExpect(jsonPath("$[1].id", is(3)))
		.andExpect(jsonPath("$[1].userid", is(1)))
		.andExpect(jsonPath("$[1].movieid", is(2)))
		.andExpect(jsonPath("$[1].username", is("saif")))
		.andExpect(jsonPath("$[1].moviename", is("AAA")));
	}
	@Test
	void testCreateRental() throws Exception{
		String content = "{\r\n" + 
				"	\"userid\": 1,\r\n" + 
				"	\"movieid\": 1\r\n" + 
				"	\r\n" + 
				"}";
		CompletableFuture<User> user = CompletableFuture.completedFuture(new User(1, "saif", "abc@gmail.com"));
		CompletableFuture<Movie> movie = CompletableFuture.completedFuture(new Movie(1, "ABC", "MMM", "XYZ", 2015, 125.5F, true));
		Rental rental = new Rental(1, 1, 1, "saif", "ABC", 2015, "XYZ", "11-05-2020 17:41", "11-05-2020 21:10", 125.5F);
		when(service.findUser(Mockito.anyInt())).thenReturn(user);
		when(service.findMovie(Mockito.anyInt())).thenReturn(movie);
		when(service.createRental(Mockito.any(Rental.class))).thenReturn(rental);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/createrental").accept(MediaType.APPLICATION_JSON).content(content).contentType(MediaType.APPLICATION_JSON)).andDo(print())
			.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)))
									   .andExpect(jsonPath("$.userid", is(1)))
									   .andExpect(jsonPath("$.movieid", is(1)))
									   .andExpect(jsonPath("$.username", is("saif")))
									   .andExpect(jsonPath("$.moviename", is("ABC")))
									   .andExpect(jsonPath("$.year", is(2015)))
									   .andExpect(jsonPath("$.director", is("XYZ")))
									   .andExpect(jsonPath("$.rentaldate", is("11-05-2020 17:41")))
									   .andExpect(jsonPath("$.duedate", is("11-05-2020 21:10")))
									   .andExpect(jsonPath("$.rent", is(125.5)));
		
	}
	@Test
	void testDeleteByUser() throws Exception{
		when(service.getRentalByUserId(Mockito.anyInt())).thenReturn(Stream.of(
				new Rental(1, 1, 1, "saif", "BDE", 2014, "OPE", "11-05-2020 17:41", "18-05-2020 17:41", 120.5F), 
				new Rental(3, 1, 2, "saif", "AAA", 2014, "ZZZ", "11-05-2020 21:10", "18-05-2020 21:10", 120.5F)).collect(Collectors.toList()));
		mockMvc.perform(MockMvcRequestBuilders.delete("/delete/userid/1").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}
	@Test
	void testDeleteById() throws Exception{
		Optional<Rental> rental = Optional.of(new Rental(1, 1, 1, "saif", "BDE", 2014 , "OPE", "11-05-2020 17:41", "18-05-2020 17:41", 120.5F));
		when(service.findById(Mockito.anyInt())).thenReturn(rental);
		mockMvc.perform(MockMvcRequestBuilders.delete("/delete/id/1").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}
}
