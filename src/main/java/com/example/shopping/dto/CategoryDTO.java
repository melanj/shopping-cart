package com.example.shopping.dto;

import lombok.Data;

/**
 * Represents a product category data transfer object.
 */
@Data
public class CategoryDTO {

    /**
     * The Category identifier.
     */
    private Long id;

    /**
     * The Category name.
     */
    private String name;

    /**
     * The Category description.
     */
    private String description;

}
