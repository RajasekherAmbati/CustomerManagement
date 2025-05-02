package com.customer.management.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.customer.management.dto.CustomerEntity;



@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
	
	
    Optional<CustomerEntity> findByNameIgnoreCase(String name);
	
	Optional<CustomerEntity> findByEmail(String email);

}