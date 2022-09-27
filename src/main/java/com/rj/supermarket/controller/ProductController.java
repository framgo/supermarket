package com.rj.supermarket.controller;

import com.rj.supermarket.model.Product;
import com.rj.supermarket.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "stock")
@Tag(name = "Super Market Controller", description = "Control that manages the registration of a product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/market", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register a product", responses = {@ApiResponse(description = "Success when registering", responseCode = "201", content = {@Content})})
    ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(this.productService.save(product), HttpStatus.CREATED);
    }

    @PutMapping(value = "/market", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a product", responses = {@ApiResponse(description = "Update success", responseCode = "204", content = {@Content})})
    ResponseEntity<?> update(@RequestBody Product product) {
        this.productService.save(product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/market/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update product supplier", responses = {@ApiResponse(description = "Supplier updated successfully", responseCode = "204", content = {@Content})})
    ResponseEntity<Product> updateProvider(@PathVariable UUID id, @RequestBody String provider) {
        this.productService.save(id, provider);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/market", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List the products", responses = {@ApiResponse(description = "Success in listing products", responseCode = "200", content = {@Content})})
    ResponseEntity<List<Product>> findAll() {
        return new ResponseEntity<>(this.productService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/market/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find a product by id", responses = {@ApiResponse(description = "Success searching for product by id", responseCode = "200", content = {@Content})})
    ResponseEntity<Product> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(this.productService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/market/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete a product by id", responses = {@ApiResponse(description = "Success in deleting the product", responseCode = "204", content = {@Content})})
    ResponseEntity<?> deleteByID(@PathVariable UUID id) {
        this.productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/market/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete all products", responses = {@ApiResponse(description = "Success in deleting all products", responseCode = "204", content = {@Content})})
    ResponseEntity<?> deleteAll() {
        this.productService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}