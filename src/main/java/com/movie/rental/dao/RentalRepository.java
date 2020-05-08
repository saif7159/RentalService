package com.movie.rental.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.rental.model.Rental;

@Repository
@Transactional
public interface RentalRepository extends JpaRepository<Rental, Integer> {
	public List<Rental> findByUserid(Integer userid);

	public List<Rental> findByMoviename(String movie);

	public List<Rental> findByUsername(String user);

//	public void deleteByUserid(Integer userid);
}
