package com.rj.supermarket.repository;

import com.rj.supermarket.model.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Modifying
    @Transactional
    @Query(value = "update Product p set p.provider = :provider where p.productId = :id")
    void updateProvider(UUID id, String provider);
}

