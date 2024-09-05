package com.service.product.service;

import com.service.product.dto.ProductRequest;
import com.service.product.dto.ProductResponse;
import com.service.product.model.Product;
import com.service.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        log.info("productRequest - {}", productRequest);
        Product product = Product.builder()
                .price(productRequest.price())
                .name(productRequest.name())
                .description(productRequest.description()).build();
        return Optional.of(productRepository.save(product))
                .map(p -> new ProductResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice()))
                .get();
    }

    public List<ProductResponse> getAllProducts() {
       return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice()))
                .toList();
    }
}
