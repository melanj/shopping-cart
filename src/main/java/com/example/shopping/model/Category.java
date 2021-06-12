package com.example.shopping.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Represents a product Category.
 */
@Entity
@Data
public class Category {

    /**
     * The Category identifier.
     */
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The Category name.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The Category description.
     */
    @Column(nullable = false)
    private String description;

}
