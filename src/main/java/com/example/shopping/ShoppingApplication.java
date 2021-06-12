package com.example.shopping;

import com.example.shopping.model.Category;
import com.example.shopping.model.Customer;
import com.example.shopping.model.Product;
import com.example.shopping.repository.CategoryRepository;
import com.example.shopping.repository.CustomerRepository;
import com.example.shopping.repository.ProductRepository;
import com.github.javafaker.Faker;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.stream.IntStream;

/**
 * Main class of the sample shopping card application.
 */
@SpringBootApplication
public class ShoppingApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ShoppingApplication.class);

    @Resource
    private CustomerRepository customerRepository;

    @Resource
    private ProductRepository productRepository;

    @Resource
    private CategoryRepository categoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(ShoppingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        final Faker faker = new Faker();

        IntStream.rangeClosed(1, 10)
                .forEach(i -> {
                    final Customer customer = new Customer();
                    String name = faker.name().fullName();
                    customer.setName(name);
                    customer.setAddress(faker.address().fullAddress());
                    customer.setUsername("user" + i);
                    LOG.info("Creating customer " + name);
                    customerRepository.save(customer);
                });

        IntStream.rangeClosed(0, 10)
                .forEach(i -> {
                    final Category category = new Category();
                    String name = faker.commerce().productName();
                    category.setName(name);
                    category.setDescription(name);
                    categoryRepository.save(category);
                    LOG.info("Creating category " + name);
                });

        IntStream.rangeClosed(0, 100)
                .forEach(i -> {
                    final Product product = new Product();
                    String name = faker.commerce().productName();
                    product.setName(name);
                    product.setTitle(name);
                    product.setCategory(categoryRepository.findById(i % 10 + 1L).orElseThrow());
                    product.setPrice(new BigDecimal(faker.commerce().price()));
                    product.setTax(new BigDecimal(1));
                    LOG.info("Creating product " + name);
                    productRepository.save(product);
                });
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
