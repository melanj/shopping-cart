package com.example.shopping.service;

import com.example.shopping.model.Customer;
import com.example.shopping.repository.CustomerRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class CustomerService {

    @Resource
    private CustomerRepository customerRepository;

    public Optional<Customer> getLoggedInCustomer() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return customerRepository.findByUsername(username);
    }
}
