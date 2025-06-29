package com.princez1.ZShop.repositories;

import com.princez1.ZShop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
