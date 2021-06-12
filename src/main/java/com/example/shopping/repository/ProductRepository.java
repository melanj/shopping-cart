package com.example.shopping.repository;

import com.example.shopping.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository providing access to the {@link Product} data.
 */
public interface ProductRepository  extends JpaRepository<Product, Long> {
}
