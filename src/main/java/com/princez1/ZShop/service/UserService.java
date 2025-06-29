package com.princez1.ZShop.service;

import com.princez1.ZShop.dtos.UpdateUserDTO;
import com.princez1.ZShop.dtos.UserDTO;
import com.princez1.ZShop.entities.User;
import com.princez1.ZShop.exceptions.DataNotFoundException;
import com.princez1.ZShop.exceptions.InvalidPasswordException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password, Long roleId) throws Exception;
    User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception;

    Page<User> findAll(String keyword, Pageable pageable) throws Exception;
    void resetPassword(Long userId, String newPassword)
            throws InvalidPasswordException, DataNotFoundException;
    public void blockOrEnable(Long userId, Boolean active) throws DataNotFoundException;
}
