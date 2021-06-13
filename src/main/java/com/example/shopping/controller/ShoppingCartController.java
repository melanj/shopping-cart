package com.example.shopping.controller;

import com.example.shopping.dto.CartItemDTO;
import com.example.shopping.dto.CustomerDTO;
import com.example.shopping.dto.ProductDTO;
import com.example.shopping.dto.ShoppingCartDTO;
import com.example.shopping.model.Customer;
import com.example.shopping.model.Product;
import com.example.shopping.repository.ProductRepository;
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
import java.math.BigDecimal;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/mycart", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShoppingCartController {

    private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartController.class);

    @Resource
    private ShoppingCartService shoppingCartService;

    @Resource
    private CustomerService customerService;

    @Resource
    private ShoppingCartDTO shoppingCart;

    @Resource
    private ProductRepository productRepository;

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

    @GetMapping
    public ResponseEntity<ShoppingCartDTO> getCart() {
        ShoppingCartDTO myCart = new ShoppingCartDTO();
        myCart.setCustomer(modelMapper.map(customerService.getLoggedInCustomer().orElseThrow(),CustomerDTO.class));
        myCart.setTotalAmount(shoppingCart.getTotalAmount());
        myCart.setTotalDiscount(shoppingCart.getTotalDiscount());
        myCart.setTotalVat(shoppingCart.getTotalVat());
        myCart.setShipmentCost(shoppingCart.getShipmentCost());
        myCart.setCoupons(shoppingCart.getCoupons());
        myCart.setCartItems(shoppingCart.getCartItems());
        return ResponseEntity.ok(myCart);
    }

    @PostMapping("/items")
    public ResponseEntity<Void> addItem(@RequestBody CartItemDTO item) {
        Long productId = item.getProduct().getId();
        Product product = productRepository.findById(productId).orElseThrow();
        item.setProduct(modelMapper.map(product, ProductDTO.class));
        BigDecimal price = product.getPrice().add(product.getTax());
        item.setAmount(price.multiply(new BigDecimal(item.getQuantity())));
        item.setDiscount(new BigDecimal(0));
        shoppingCart.getCartItems().add(item);
        MultiValueMap<String, String> headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productId).toUri();
        headers.add(HttpHeaders.LOCATION, location.toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<Void> updateItem(@PathVariable final Long productId, @RequestBody CartItemDTO item) {
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeItem(@PathVariable final Long productId) {
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/items")
    public ResponseEntity<Set<CartItemDTO>> listItem() {
        return ResponseEntity.ok(shoppingCart.getCartItems());
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        shoppingCart.getCartItems().clear();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/submit")
    public ResponseEntity<Void> submitCart() {
        Customer customer = customerService.getLoggedInCustomer().orElseThrow();
        shoppingCart.setCustomer(modelMapper.map(customer, CustomerDTO.class));
        LOG.info(shoppingCart.toString());
        MultiValueMap<String, String> headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand("#").toUri();
        headers.add(HttpHeaders.LOCATION, location.toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}
