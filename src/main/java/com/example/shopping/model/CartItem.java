package com.example.shopping.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Represents a shopping cart item linked to a shopping cart.
 */
@Entity
@Data
public class CartItem {

    /**
     * The CartItem identifier
     */
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The quantity of the product purchased.
     */
    @Column(nullable = false)
    private Long quantity;

    /**
     * The total discount of this cart item
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    /**
     * The total discount of this cart item
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discount;

    /**
     * The {@link Product} instance representing the product of the CartItem.
     */
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * The {@link ShoppingCart} instance representing the ShoppingCart of the CartItem.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private ShoppingCart cart;

}
