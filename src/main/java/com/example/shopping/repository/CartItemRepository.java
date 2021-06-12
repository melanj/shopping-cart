package com.example.shopping.repository;

import com.example.shopping.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository providing access to the {@link CartItem} data.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
