package com.example.shopping.model;

import lombok.Data;

import javax.persistence.*;


/**
 * Represents a Customer.
 */
@Entity
@Data
public class Customer {

    /**
     * The Customer identifier
     */
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of Customer
     */
    @Column(nullable = false)
    private String name;

    /**
     * The address of Customer
     */
    @Column(nullable = false)
    private String address;

}
