package com.princez1.ZShop.repositories;

import com.princez1.ZShop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
