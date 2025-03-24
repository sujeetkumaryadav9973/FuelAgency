package com.faos.repository;
import org.springframework.stereotype.Repository;

import com.faos.model.Customer;
import com.faos.model.enums.ConnectionStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{
	List<Customer> findByConnectionStatus(ConnectionStatus connectionStatus);
}
