package com.princez1.ZShop.repositories;

import com.princez1.ZShop.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
