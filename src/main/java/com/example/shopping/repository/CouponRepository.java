package com.example.shopping.repository;

import com.example.shopping.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository providing access to the {@link Coupon} data.
 */
public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
