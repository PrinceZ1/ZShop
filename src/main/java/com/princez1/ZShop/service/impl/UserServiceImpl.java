package com.princez1.ZShop.service.impl;

import com.princez1.ZShop.dtos.UpdateUserDTO;
import com.princez1.ZShop.dtos.UserDTO;
import com.princez1.ZShop.entities.Role;
import com.princez1.ZShop.entities.User;
import com.princez1.ZShop.exceptions.DataNotFoundException;
import com.princez1.ZShop.exceptions.InvalidPasswordException;
import com.princez1.ZShop.repositories.RoleRepository;
import com.princez1.ZShop.repositories.UserRepository;
import com.princez1.ZShop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        // Kiểm tra số điện thoại đã tồn tại chưa
        if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            throw new Exception("Phone number already exists");
        }

        // Tìm role theo ID
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));

        // Tạo user mới
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .role(role)
                .active(true)
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password, Long roleId) throws Exception {
        // Tìm user theo số điện thoại
        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new DataNotFoundException("Invalid phone number or password"));

        // Kiểm tra role
        if (!existingUser.getRole().getId().equals(roleId)) {
            throw new Exception("Role does not match");
        }

        // Kiểm tra mật khẩu
        if (!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new Exception("Invalid phone number or password");
        }

        // Kiểm tra user có active không
        if (!existingUser.isActive()) {
            throw new Exception("User is blocked");
        }

        // Trả về thông báo đăng nhập thành công (sau này sẽ trả về JWT token)
        return "Login successful for user: " + existingUser.getFullName();
    }

    @Override
    public User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // Cập nhật thông tin user
        if (updatedUserDTO.getFullName() != null) {
            existingUser.setFullName(updatedUserDTO.getFullName());
        }
        if (updatedUserDTO.getAddress() != null) {
            existingUser.setAddress(updatedUserDTO.getAddress());
        }
        if (updatedUserDTO.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(updatedUserDTO.getDateOfBirth());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public Page<User> findAll(String keyword, Pageable pageable) {
        return userRepository.findAll(keyword, pageable);
    }

    @Override
    public void resetPassword(Long userId, String newPassword) throws InvalidPasswordException, DataNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // Kiểm tra mật khẩu mới có hợp lệ không
        if (newPassword == null || newPassword.length() < 6) {
            throw new InvalidPasswordException("Password must be at least 6 characters");
        }

        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void blockOrEnable(Long userId, Boolean active) throws DataNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        user.setActive(active);
        userRepository.save(user);
    }
}
