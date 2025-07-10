package com.princez1.ZShop.service;

import com.princez1.ZShop.dto.UpdateUserDTO;
import com.princez1.ZShop.dto.UserDTO;
import com.princez1.ZShop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User createUser(UserDTO userDTO);
    String login(String phoneNumber, String password) throws Exception;
    User getUserDetailsFromToken(String token);
    User getUserDetailsFromRefreshToken(String token);
    User updateUser(Long userId, UpdateUserDTO updatedUserDTO);
    Page<User> findAll(String keyword, Pageable pageable);
    void blockOrEnable(Long userId, Boolean active);
}
