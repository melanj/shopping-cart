package com.example.shopping.dto;

import lombok.Data;

/**
 * Represents a Customer data transfer object.
 */
@Data
public class CustomerDTO {

    /**
     * The Customer identifier
     */
    private Long id;

    /**
     * The name of Customer
     */
    private String name;

    /**
     * The address of Customer
     */
    private String address;

    /**
     * The login username of Customer
     */
    private String username;

}
