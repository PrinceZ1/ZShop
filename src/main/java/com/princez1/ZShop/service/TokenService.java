package com.princez1.ZShop.service;

import com.princez1.ZShop.entity.Token;
import com.princez1.ZShop.entity.User;

public interface TokenService {
    Token addToken(User user, String token);
    Token refreshToken(String refreshToken, User user) throws Exception;
}
