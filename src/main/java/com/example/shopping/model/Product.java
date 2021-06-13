package com.example.shopping.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * Represents a Product linked to a shopping cart item.
 */
@Entity
@Data
public class Product {

    /**
     * The Product identifier.
     */
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The Product title.
     */
    @Column(nullable = false)
    private String title;

    /**
     * The Product name.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The price of the item.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * Applicable tax.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tax;

    /**
     * The {@link Category} instance representing the category of the product.
     */
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
