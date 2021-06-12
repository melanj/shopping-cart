package com.example.shopping.dto;

import lombok.Data;

/**
 * Represents a coupon data transfer object.
 */
@Data
public class CouponDTO {

    /**
     * The Customer identifier.
     */
    private Long id;

    /**
     * The Coupon title.
     */
    private String title;

    /**
     * The Strategy to calculate discount.
     */
    private String discountStrategy;

}
