package com.example.shopping.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Represents a product category data transfer object.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
