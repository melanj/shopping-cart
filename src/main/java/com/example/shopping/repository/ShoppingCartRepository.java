package com.example.shopping.repository;

import com.example.shopping.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository providing access to the {@link ShoppingCart} data.
 */
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
