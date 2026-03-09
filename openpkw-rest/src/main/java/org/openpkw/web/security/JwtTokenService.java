package org.openpkw.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Service for generating and validating JWT tokens using JJWT.
 */
@Service
public class JwtTokenService {

    private static final long ACCESS_TOKEN_VALIDITY_MS = 3600_000; // 1 hour
    private static final long REFRESH_TOKEN_VALIDITY_MS = 86400_000; // 24 hours

    private final SecretKey signingKey;

    public JwtTokenService(SecretKey jwtSigningKey) {
        this.signingKey = jwtSigningKey;
    }

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, ACCESS_TOKEN_VALIDITY_MS, "access");
    }

    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, REFRESH_TOKEN_VALIDITY_MS, "refresh");
    }

    private String generateToken(Authentication authentication, long validityMs, String tokenType) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityMs);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(authentication.getName())
                .claim("authorities", authorities)
                .claim("token_type", tokenType)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
