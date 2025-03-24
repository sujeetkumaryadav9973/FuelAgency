package com.faos.repository;


import com.faos.model.Booking;
import com.faos.model.Customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByCustomer(Customer customer);

}