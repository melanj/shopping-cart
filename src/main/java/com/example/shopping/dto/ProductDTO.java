package com.example.shopping.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;


/**
 * Represents a Product data transfer object.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    /**
     * The Product identifier.
     */
    private Long id;

    /**
     * The Product title.
     */
    private String title;

    /**
     * The Product name.
     */
    private String name;

    /**
     * The price of the item.
     */
    private BigDecimal price;

    /**
     * Applicable tax.
     */
    private BigDecimal tax;

    /**
     * The {@link CategoryDTO} instance representing the category of the product.
     */
    private CategoryDTO category;

}
