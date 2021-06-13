package com.example.shopping.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Represents a Shopping cart data transfer object.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingCartDTO {

    /**
     * The ShoppingCart identifier
     */
    private Long id;

    /**
     * The total amount of the shopping cart.
     */
    private BigDecimal totalAmount;

    /**
     * The total VAT amount.
     */
    private BigDecimal totalVat;

    /**
     * The total VAT amount.
     */
    private BigDecimal shipmentCost;

    /**
     * The total discount amount.
     */
    private BigDecimal totalDiscount;

    /**
     * The {@link CustomerDTO} instance representing the customer of the ShoppingCart.
     */
    private CustomerDTO customer;

    /**
     * The set of {@link CouponDTO} instances representing coupons applied to the ShoppingCart.
     */
    private Set<CouponDTO> coupons;

    /**
     * The set of {@link CartItemDTO} instances representing coupons applied to the ShoppingCart.
     */
    private Set<CartItemDTO> cartItems;

}
