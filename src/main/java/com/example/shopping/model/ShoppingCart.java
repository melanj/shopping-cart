package com.example.shopping.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Represents a Shopping cart.
 */
@Entity
@Data
public class ShoppingCart {

    /**
     * The ShoppingCart identifier
     */
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The total amount of the shopping cart.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    /**
     * The total VAT amount.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalVat;

    /**
     * The total VAT amount.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal shipmentCost;

    /**
     * The total discount amount.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalDiscount;

    /**
     * The {@link Customer} instance representing the customer of the ShoppingCart.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    /**
     * The set of {@link Coupon} instances representing coupons applied to the ShoppingCart.
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupons_id", nullable = false)
    private Set<Coupon> coupons;

}
