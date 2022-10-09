package com.rj.supermarket.service;

import com.rj.supermarket.domain.model.Product;
import com.rj.supermarket.repository.ProductRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return (Product)this.productRepository.save(product);
    }

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    public Product findById(UUID id) {
        return (Product)this.productRepository.findById(id).orElse(new Product());
    }

    public void deleteById(UUID id) {
        this.productRepository.deleteById(id);
    }

    public void deleteAll() {
        this.productRepository.deleteAll();
    }

    public void save(UUID id, String provider) {
        productRepository.updateProvider(id, provider);
    }
}
