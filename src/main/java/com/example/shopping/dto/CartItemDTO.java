package com.example.shopping.dto;

import com.example.shopping.model.Product;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Represents a shopping cart item data transfer object.
 */
@Data
public class CartItemDTO {

    /**
     * The CartItem identifier
     */
    private Long id;

    /**
     * The quantity of the product purchased.
     */
    private Long quantity;

    /**
     * The total discount of this cart item
     */
    private BigDecimal amount;

    /**
     * The total discount of this cart item
     */
    private BigDecimal discount;

    /**
     * The {@link Product} instance representing the product of the CartItem.
     */
    private Product product;

    /**
     * The {@link ShoppingCartDTO} instance representing the ShoppingCart of the CartItem.
     */
    private ShoppingCartDTO cart;

}
