package com.example.shopping.controller;

import com.example.shopping.dto.ProductDTO;
import com.example.shopping.model.Product;
import com.example.shopping.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * REST controller implementation to provide REST API for product management.
 */
@RestController
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Resource
    private ProductRepository productRepository;

    @Resource
    private ModelMapper modelMapper;

    /**
     * Returns a {@link Page} of {@link ProductDTO} list  meeting the paging restriction
     * provided in the {@code Pageable}  object.
     *
     * @param pageable Pageable object
     * @return a page of products
     */
    @GetMapping
    public Page<ProductDTO> getProducts(final Pageable pageable) {
        Page<Product> entities = productRepository.findAll(pageable);
        LOG.debug("listing products based on the paging restriction");
        return entities.map(product -> modelMapper.map(product, ProductDTO.class));
    }


}
