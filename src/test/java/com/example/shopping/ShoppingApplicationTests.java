package com.example.shopping;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ShoppingApplication.class)
class ShoppingApplicationTests extends AbstractTestNGSpringContextTests {

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

    @Test
    public void testBootstrap() throws Exception {
        URI uri = new URI("http://localhost:" + randomServerPort + "/actuator");
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testPurchaseSingleItemAndGetTotal() throws Exception {
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/mycart");
        HttpEntity<String> request = new HttpEntity<>(headersUser1);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
        assertEquals(200, result.getStatusCodeValue());
        setCookie(result, headersUser1);

        String item1 = "{\n" +
                "    \"quantity\": 2,\n" +
                "    \"product\": {\n" +
                "      \"id\": 97\n" +
                "    }\n" +
                "  }\n";
        uri = new URI("http://localhost:" + randomServerPort + "/api/mycart/items");

        request = new HttpEntity<>(item1, headersUser1);
        result = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
        assertEquals(201, result.getStatusCodeValue());

        uri = new URI("http://localhost:" + randomServerPort + "/api/mycart");
        request = new HttpEntity<>(headersUser1);
        result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
        assertEquals(200, result.getStatusCodeValue());
        System.out.println("test1 =" + result.getBody());

    }

    @Test
    public void testPurchaseMultipleItemsAndGetTotal() throws Exception {
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/mycart");
        HttpEntity<String> request = new HttpEntity<>(headersUser2);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
        assertEquals(200, result.getStatusCodeValue());
        setCookie(result, headersUser2);

        String item1 = "{\n" +
                "    \"quantity\": 3,\n" +
                "    \"product\": {\n" +
                "      \"id\": 30\n" +
                "    }\n" +
                "  }\n";
        uri = new URI("http://localhost:" + randomServerPort + "/api/mycart/items");

        request = new HttpEntity<>(item1, headersUser2);
        result = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
        assertEquals(201, result.getStatusCodeValue());

        String item2 = "{\n" +
                "    \"quantity\": 5,\n" +
                "    \"product\": {\n" +
                "      \"id\": 60\n" +
                "    }\n" +
                "  }\n";

        request = new HttpEntity<>(item2, headersUser2);
        result = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
        assertEquals(201, result.getStatusCodeValue());

        uri = new URI("http://localhost:" + randomServerPort + "/api/mycart");
        request = new HttpEntity<>(headersUser2);
        result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
        assertEquals(200, result.getStatusCodeValue());
        System.out.println("Test2 =" + result.getBody());
    }

    private static void setCookie(ResponseEntity<String> result, HttpHeaders headers) {
        List<String> cookie = result.getHeaders().get("Set-Cookie");
        assertNotNull(cookie);
        assertFalse(cookie.isEmpty());
        headers.set("Cookie", cookie.get(0));
    }

}
