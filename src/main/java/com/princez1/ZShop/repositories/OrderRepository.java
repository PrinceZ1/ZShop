package com.princez1.ZShop.repositories;

import com.princez1.ZShop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
