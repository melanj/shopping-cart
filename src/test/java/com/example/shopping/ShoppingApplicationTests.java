package com.example.shopping;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		headersUser1.setBasicAuth("user1","user1Pass");
		headersUser2.setContentType(MediaType.APPLICATION_JSON);
		headersUser2.setBasicAuth("user2","user2Pass");
	}

	@Test
	public void testBootstrap() throws Exception {
		URI uri = new URI("http://localhost:" + randomServerPort + "/actuator");
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	public void testBootstrap33() throws Exception {
		URI uri = new URI("http://localhost:" + randomServerPort + "/api/carts/");
		HttpEntity<String> request = new HttpEntity<>(headersUser1);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
		assertEquals(201, result.getStatusCodeValue());
	}

}
