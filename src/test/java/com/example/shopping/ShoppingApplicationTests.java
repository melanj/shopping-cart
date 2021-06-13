package com.example.shopping;

import com.example.shopping.dto.CartItemDTO;
import com.example.shopping.dto.ProductDTO;
import com.example.shopping.dto.ShoppingCartDTO;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Random;

import static org.testng.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ShoppingApplication.class)
class ShoppingApplicationTests extends AbstractTestNGSpringContextTests {

    public static final String LOCALHOST = "http://localhost:";
    @LocalServerPort
    private int randomServerPort;
    private RestTemplate restTemplate;
    private final HttpHeaders headersUser1 = new HttpHeaders();
    private final HttpHeaders headersUser2 = new HttpHeaders();


    @BeforeMethod
    public void setUp() {
        restTemplate = new RestTemplate();
        headersUser1.setContentType(MediaType.APPLICATION_JSON);
        headersUser1.setBasicAuth("user1", "user1Pass");
        headersUser2.setContentType(MediaType.APPLICATION_JSON);
        headersUser2.setBasicAuth("user2", "user2Pass");
    }

    @Test(priority = 0)
    public void testBootstrap() throws Exception {
        URI uri = new URI(LOCALHOST + randomServerPort + "/actuator");
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test(priority = 1)
    public void testPurchaseSingleItemAndGetTotal() throws Exception {
        // doing an API call to get the content of the cart associated with current session (user1)
        URI uri = new URI(LOCALHOST + randomServerPort + "/api/mycart");
        HttpEntity<String> request = new HttpEntity<>(headersUser1);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
        assertEquals(200, result.getStatusCodeValue());
        setCookie(result, headersUser1); // save the session cookie to use with subsequent requests.

        // doing an API call to get products and select a random product
        List<ProductDTO> products = getProducts(headersUser1);
        Random random = new Random();
        ProductDTO product = products.stream().skip(random.nextInt(products.size())).findFirst().orElseThrow();

        BigDecimal exceptedTotal = new BigDecimal("0.0");
        CartItemDTO item = new CartItemDTO();
        item.setQuantity((long) (random.nextInt(5) + 1));
        item.setProduct(product);

        BigDecimal price = product.getPrice().add(product.getTax());
        exceptedTotal = exceptedTotal.add(price.multiply(new BigDecimal(item.getQuantity())));

        // doing an API call to add the select product into cart.
        uri = new URI(LOCALHOST + randomServerPort + "/api/mycart/items");
        HttpEntity<CartItemDTO> itemAddRequest = new HttpEntity<>(item, headersUser1);
        result = restTemplate.exchange(uri, HttpMethod.POST, itemAddRequest, String.class);
        assertEquals(201, result.getStatusCodeValue());

        // doing an API call to get the content of the cart
        uri = new URI(LOCALHOST + randomServerPort + "/api/mycart");
        request = new HttpEntity<>(headersUser1);
        ResponseEntity<ShoppingCartDTO> cartResponse = restTemplate.exchange(uri,
                HttpMethod.GET, request, ShoppingCartDTO.class);
        assertEquals(200, cartResponse.getStatusCodeValue());
        assertNotNull(cartResponse.getBody());
        exceptedTotal = exceptedTotal.multiply(new BigDecimal("1.15"));
        assertEquals(exceptedTotal, cartResponse.getBody().getTotalAmount());

        // doing an API call to clear the cart.
        uri = new URI(LOCALHOST + randomServerPort + "/api/mycart");
        request = new HttpEntity<>(headersUser1);
        result = restTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);
        assertEquals(204, result.getStatusCodeValue());
    }


    @Test(priority = 2)
    public void testPurchaseMultipleItemsAndGetTotal() throws Exception {
        // doing an API call to get the content of the cart associated with current session (user2)
        URI uri = new URI(LOCALHOST + randomServerPort + "/api/mycart");
        HttpEntity<String> request = new HttpEntity<>(headersUser2);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
        assertEquals(200, result.getStatusCodeValue());
        setCookie(result, headersUser2);

        // doing an API call to get products and select a random product
        List<ProductDTO> products = getProducts(headersUser2);
        Random random = new Random();
        ProductDTO product1 = products.stream().skip(random.nextInt(products.size())).findFirst().orElseThrow();

        BigDecimal exceptedTotal = new BigDecimal("0.0");
        CartItemDTO item1 = new CartItemDTO();
        item1.setQuantity((long) (random.nextInt(5) + 1));
        item1.setProduct(product1);

        BigDecimal price1 = product1.getPrice().add(product1.getTax());
        exceptedTotal = exceptedTotal.add(price1.multiply(new BigDecimal(item1.getQuantity())));

        // doing an API call to add the select product into cart.
        uri = new URI(LOCALHOST + randomServerPort + "/api/mycart/items");
        HttpEntity<CartItemDTO> itemAddRequest = new HttpEntity<>(item1, headersUser2);
        result = restTemplate.exchange(uri, HttpMethod.POST, itemAddRequest, String.class);
        assertEquals(201, result.getStatusCodeValue());

        // Select another random product
        ProductDTO product2 = products.stream().skip(random.nextInt(products.size())).findFirst().orElseThrow();

        CartItemDTO item2 = new CartItemDTO();
        item2.setQuantity((long) (random.nextInt(5) + 1));
        item2.setProduct(product2);

        BigDecimal price2 = product2.getPrice().add(product2.getTax());
        exceptedTotal = exceptedTotal.add(price2.multiply(new BigDecimal(item2.getQuantity())));

        // doing an API call to add the select product into cart.
        itemAddRequest = new HttpEntity<>(item2, headersUser2);
        result = restTemplate.exchange(uri, HttpMethod.POST, itemAddRequest, String.class);
        assertEquals(201, result.getStatusCodeValue());

        // doing an API call to get the content of the cart
        uri = new URI(LOCALHOST + randomServerPort + "/api/mycart");
        request = new HttpEntity<>(headersUser2);
        ResponseEntity<ShoppingCartDTO> cartResponse = restTemplate.exchange(uri,
                HttpMethod.GET, request, ShoppingCartDTO.class);
        assertEquals(200, cartResponse.getStatusCodeValue());
        assertNotNull(cartResponse.getBody());
        exceptedTotal = exceptedTotal.multiply(new BigDecimal("1.15"));
        assertEquals(exceptedTotal, cartResponse.getBody().getTotalAmount());

        // doing an API call to submit the cart to process.
        uri = new URI(LOCALHOST + randomServerPort + "/api/mycart/submit");
        request = new HttpEntity<>(headersUser2);
        result = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
        assertEquals(201, result.getStatusCodeValue());
    }

    private static void setCookie(ResponseEntity<String> result, HttpHeaders headers) {
        List<String> cookie = result.getHeaders().get("Set-Cookie");
        assertNotNull(cookie);
        assertFalse(cookie.isEmpty());
        headers.set("Cookie", cookie.get(0));
    }

    private List<ProductDTO> getProducts(HttpHeaders headers) throws Exception {
        URI uri = new URI(LOCALHOST + randomServerPort + "/api/products");
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<DummyPageImpl<ProductDTO>> result = restTemplate.exchange(uri, HttpMethod.GET, request,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        return result.getBody().getContent();
    }

}
