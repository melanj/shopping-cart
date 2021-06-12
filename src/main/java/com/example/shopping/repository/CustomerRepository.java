package com.example.shopping.repository;

import com.example.shopping.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository providing access to the {@link Customer} data.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
