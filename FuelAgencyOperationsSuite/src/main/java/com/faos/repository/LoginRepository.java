package com.faos.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.faos.model.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
	Optional<Login> findByCustomer_CustomerId(Long customerId);
}
