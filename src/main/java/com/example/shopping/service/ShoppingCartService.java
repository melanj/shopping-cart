package com.example.shopping.service;

import com.example.shopping.dto.ShoppingCartDTO;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {


    public ShoppingCartService() {
    }

    public void clearCart(ShoppingCartDTO shoppingCartDTO) {
        shoppingCartDTO.getCartItems().clear();
    }

    public void addToCart(ShoppingCartDTO shoppingCartDTO, ShoppingCartDTO item) {
        shoppingCartDTO.getCartItems().clear();
    }


}
