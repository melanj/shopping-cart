package com.example.shopping.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Represents a coupon data transfer object.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
