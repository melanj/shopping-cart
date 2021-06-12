package com.example.shopping.controller;

import com.example.shopping.dto.CartItemDTO;
import com.example.shopping.dto.CustomerDTO;
import com.example.shopping.dto.ShoppingCartDTO;
import com.example.shopping.model.Customer;
import com.example.shopping.service.CustomerService;
import com.example.shopping.service.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.HashSet;

@RestController
@RequestMapping(value = "/api/carts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShoppingCartController {

    private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartController.class);

    @Resource
    private ShoppingCartService shoppingCartService;

    @Resource
    private CustomerService customerService;

    @Resource
    private ShoppingCartDTO shoppingCart;

    @Resource
    private ModelMapper modelMapper;

    @Bean
    @Scope(
            value = WebApplicationContext.SCOPE_SESSION,
            proxyMode = ScopedProxyMode.TARGET_CLASS)
    private ShoppingCartDTO shoppingCart() {
        ShoppingCartDTO cart = new ShoppingCartDTO();
        cart.setCartItems(new HashSet<>());
        return cart;
    }

    @PostMapping
    public ResponseEntity<Void> createCart() {
        Customer customer = customerService.getLoggedInCustomer().orElseThrow();
        shoppingCart.setCustomer(modelMapper.map(customer, CustomerDTO.class));
        LOG.info(shoppingCart.toString());
        MultiValueMap<String, String> headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand("#").toUri();
        headers.add(HttpHeaders.LOCATION, location.toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/items/")
    public ResponseEntity<Void> addItem(@RequestBody CartItemDTO item) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<Void> updateItem(@PathVariable final Long productId, @RequestBody CartItemDTO item) {
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeItem(@PathVariable final Long productId) {

        return ResponseEntity.accepted().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        shoppingCart.getCartItems().clear();
        return ResponseEntity.noContent().build();
    }

}
