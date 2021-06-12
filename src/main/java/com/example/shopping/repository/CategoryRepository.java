package com.example.shopping.repository;

import com.example.shopping.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository providing access to the {@link Category} data.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
