package com.example.demo.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.LoginResponse;

@Service
public class SecurityService {
    private final JwtEncoder jwtEncoder;

    public SecurityService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Value("${api.secret.key}")
    private String secretKey;

    @Value("${access.token.expiration.time}")
    private long accessTokenExpiration;

    @Value("${refresh.token.expiration.time}")
    private long refreshTokenExpiration;

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    /**
     * @param authentication Authentication information of user
     * @return JWT Token
     */
    public String createAccessToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);

        // Payload: Contain authentication information
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(authentication.getName())
                .claim("user", authentication)
                .build();

        // Header: Contain using signing algorithm
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        // Base64Encode Header, Payload and Secret Key (after using Signing algorithm)
        // to get JWT Token
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsSet)).getTokenValue();
    }

    public String createRefreshToken(String email, LoginResponse loginDTO) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);

        // Payload: Contain authentication information
        // Only include basic user info to avoid serialization issues with Instant
        // fields
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(email)
                .claim("userId", loginDTO.getUser().getId())
                .claim("username", loginDTO.getUser().getUsername())
                .claim("email", loginDTO.getUser().getEmail())
                .build();

        // Header: Contain using signing algorithm
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        // Base64Encode Header, Payload and Secret Key (after using Signing algorithm)
        // to get JWT Token
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsSet)).getTokenValue();
    }

    public static String getCurrentUserEmailLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
