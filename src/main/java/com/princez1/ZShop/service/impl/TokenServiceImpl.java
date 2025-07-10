package com.princez1.ZShop.service.impl;

import com.princez1.ZShop.component.JwtTokenUtils;
import com.princez1.ZShop.entity.Token;
import com.princez1.ZShop.entity.User;
import com.princez1.ZShop.repository.TokenRepository;
import com.princez1.ZShop.service.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{
    private static final int MAX_TOKENS = 3;
    @Value("${jwt.expiration}")
    private int expiration;

    @Value("${jwt.expiration-refresh-token}")
    private int expirationRefreshToken;

    private final TokenRepository tokenRepository;
    private final JwtTokenUtils jwtTokenUtil;

    @Transactional
    @Override
    public Token refreshToken(String refreshToken, User user) throws Exception{
        Token existingToken = tokenRepository.findByRefreshToken(refreshToken);
        if(existingToken == null) {
            throw new RuntimeException("Refresh token does not exist");
        }
        if(existingToken.getRefreshExpirationDate().compareTo(LocalDateTime.now()) < 0){
            tokenRepository.delete(existingToken);
            throw new RuntimeException("Refresh token is expired");
        }
        String token = jwtTokenUtil.generateToken(user);
        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expiration);
        existingToken.setExpirationDate(expirationDateTime);
        existingToken.setToken(token);
        existingToken.setRefreshToken(UUID.randomUUID().toString());
        existingToken.setRefreshExpirationDate(LocalDateTime.now().plusSeconds(expirationRefreshToken));
        return existingToken;
    }

    @Transactional
    @Override
    public Token addToken(User user, String token) {
        List<Token> userTokens = tokenRepository.findByUser(user);

        // Nếu số lượng token vượt quá giới hạn, xóa token cũ nhất
        if (userTokens.size() >= MAX_TOKENS) {
            Token tokenToDelete = userTokens.get(0); // có thể sắp xếp theo thời gian nếu cần
            tokenRepository.delete(tokenToDelete);
        }

        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expiration);

        Token newToken = Token.builder()
                .user(user)
                .token(token)
                .revoked(false)
                .expired(false)
                .tokenType("Bearer")
                .expirationDate(expirationDateTime)
                .refreshToken(UUID.randomUUID().toString())
                .refreshExpirationDate(LocalDateTime.now().plusSeconds(expirationRefreshToken))
                .build();

        return tokenRepository.save(newToken);
    }
}

