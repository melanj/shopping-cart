package com.example.shopping.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Represents a coupon linked to a shopping cart.
 */
@Entity
@Data
public class Coupon {

    /**
     * The Customer identifier.
     */
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The Coupon title.
     */
    @Column(nullable = false)
    private String title;


    /**
     * The Strategy to calculate discount.
     */
    @Column(nullable = false, columnDefinition = "clob")
    private String discountStrategy;

}
