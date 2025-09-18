package com.example.demo.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.UserClaims;
import com.example.demo.dto.response.UserDTO;
import com.nimbusds.jose.util.Base64;

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
    public String createAccessToken(String email, String role, UserDTO userDTO) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);

        // Payload: Contain authentication information
        UserClaims userClaims = new UserClaims(userDTO);
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(email)
                .claim("authorities", role)
                .claim("user", userClaims)
                .build();

        // Header: Contain using signing algorithm
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        // Base64Encode Header, Payload and Secret Key (after using Signing algorithm)
        // to get JWT Token
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsSet)).getTokenValue();
    }

    public String createRefreshToken(String email, UserDTO userDTO) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);

        // Payload: Contain authentication information
        UserClaims userClaims = new UserClaims(userDTO);
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(email)
                .claim("user", userClaims)
                .build();

        // Header: Contain using signing algorithm
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        // Base64Encode Header, Payload and Secret Key (after using Signing algorithm)
        // to get JWT Token
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsSet)).getTokenValue();
    }

    public Jwt checkValidRefreshToken(String refreshToken) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(getSecretKey())
                .macAlgorithm(SecurityService.JWT_ALGORITHM).build();
        try {
            return jwtDecoder.decode(refreshToken);
        } catch (Exception e) {
            throw e;
        }
    }

    public static String getCurrentUserEmailLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(secretKey).decode();
        return new SecretKeySpec(keyBytes, JWT_ALGORITHM.getName());
    }
}
