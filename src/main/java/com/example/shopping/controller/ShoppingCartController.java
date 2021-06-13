package com.example.shopping.controller;

import com.example.shopping.dto.CartItemDTO;
import com.example.shopping.dto.CustomerDTO;
import com.example.shopping.dto.ProductDTO;
import com.example.shopping.dto.ShoppingCartDTO;
import com.example.shopping.model.CartItem;
import com.example.shopping.model.Customer;
import com.example.shopping.model.Product;
import com.example.shopping.model.ShoppingCart;
import com.example.shopping.repository.CartItemRepository;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.repository.ShoppingCartRepository;
import com.example.shopping.service.CustomerService;
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

/**
 * REST controller implementation to provide REST API for shopping cart management.
 */
@RestController
@RequestMapping(value = "/api/mycart", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShoppingCartController {

    private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartController.class);

    @Resource
    private CustomerService customerService;

    @Resource
    private ShoppingCartDTO shoppingCart;

    @Resource
    private ProductRepository productRepository;

    @Resource
    private ShoppingCartRepository shoppingCartRepository;

    @Resource
    private CartItemRepository cartItemRepository;

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

    /**
     * Returns the Shopping cart linked with the current session
     *
     * @return a {@link ShoppingCartDTO}
     */
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

    /**
     * Adds an item to the Shopping cart linked with the current session.
     *
     * @param item CartItem entry to provide information
     * @return a ResponseEntity to returns the status.
     */
    @PostMapping("/items")
    public ResponseEntity<Void> addItem(@RequestBody CartItemDTO item) {
        Long productId = item.getProduct().getId();
        Product product = productRepository.findById(productId).orElseThrow();
        item.setProduct(modelMapper.map(product, ProductDTO.class));
        BigDecimal price = product.getPrice().add(product.getTax());
        item.setAmount(price.multiply(new BigDecimal(item.getQuantity())));
        item.setDiscount(new BigDecimal(0));
        shoppingCart.getCartItems().add(item);
        updateShoppingCart();
        return new ResponseEntity<>(getLocationHeader(productId), HttpStatus.CREATED);
    }

    /**
     * Updates an item in the Shopping cart linked with the current session.
     * @param productId product id of the item
     * @param item CartItem entry to provide information
     * @return a ResponseEntity to returns the status.
     */
    @PutMapping("/items/{productId}")
    public ResponseEntity<Void> updateItem(@PathVariable final Long productId, @RequestBody CartItemDTO item) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Removes an item from the Shopping cart linked with the current session.
     * @param productId product id of the item
     * @return a ResponseEntity to returns the status.
     */
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeItem(@PathVariable final Long productId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Returns the list of item in the Shopping cart linked with the current session.
     *
     * @return a set of CartItemDTO
     */
    @GetMapping("/items")
    public ResponseEntity<Set<CartItemDTO>> listItem() {
        return ResponseEntity.ok(shoppingCart.getCartItems());
    }

    /**
     * Clears the Shopping cart linked with the current session.
     *
     * @return a ResponseEntity to returns the status.
     */
    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        shoppingCart.getCartItems().clear();
        LOG.info("Cart cleared");
        return ResponseEntity.noContent().build();
    }

    /**
     * Submits the Shopping cart linked with the current session for processing.
     *
     * @return a ResponseEntity to returns the status.
     */
    @PostMapping("/submit")
    public ResponseEntity<Void> submitCart() {
        Customer customer = customerService.getLoggedInCustomer().orElseThrow();
        ShoppingCart cart = modelMapper.map(shoppingCart, ShoppingCart.class);
        cart.setCustomer(customer);
        Long orderId = shoppingCartRepository.save(cart).getId();
        shoppingCart.getCartItems().forEach(i -> {
            CartItem cartItem = modelMapper.map(i, CartItem.class);
            cartItem.setCart(cart);
            cartItemRepository.save(cartItem);
        });
        LOG.info("Shopping-cart summited {}",orderId );
        shoppingCart.getCartItems().clear();
        return new ResponseEntity<>(getLocationHeader(orderId), HttpStatus.CREATED);
    }

    private void updateShoppingCart() {
        BigDecimal total = shoppingCart.getCartItems().stream()
                .map(CartItemDTO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        shoppingCart.setTotalVat(total.multiply(new BigDecimal("0.15")));
        shoppingCart.setTotalAmount(total.add(shoppingCart.getTotalVat()));

        //TODO: handle shipment cost and discount
        shoppingCart.setShipmentCost(new BigDecimal("0"));
        shoppingCart.setTotalDiscount(new BigDecimal("0"));
        LOG.info("Cart updated");
    }

    private MultiValueMap<String, String> getLocationHeader(Object productId) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productId).toUri();
        headers.add(HttpHeaders.LOCATION, location.toString());
        return headers;
    }

}
