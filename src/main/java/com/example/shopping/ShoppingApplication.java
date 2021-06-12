package com.example.shopping;

import com.example.shopping.model.Customer;
import com.example.shopping.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.Resource;

/**
 * Main class of the sample shopping card application.
 */
@SpringBootApplication
public class ShoppingApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ShoppingApplication.class);

    @Resource
    private CustomerRepository customerRepository;

    @Resource
    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(ShoppingApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Customer customerA = new Customer();
        customerA.setName("CustomerA");
        customerA.setAddress("Colombo");
        LOG.info("Creating CustomerA...");
        customerRepository.save(customerA);
        Customer customerB = new Customer();
        customerB.setName("CustomerB");
        customerB.setAddress("Colombo");
        LOG.info("Creating CustomerB...");
        customerRepository.save(customerB);
        System.out.println(customerRepository.findAll());
        context.close();
    }
}
