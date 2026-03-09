package org.openpkw.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import javax.crypto.SecretKey;

/**
 * Test spring configuration
 * @author Sebastian Pogorzelski
 */
@Configuration
@ComponentScan(basePackages = {"org.openpkw.services.qr","org.openpkw.services", "org.openpkw.rest","org.openpkw.validation", "org.openpkw.web.utils"})
public class TestAppConfig {

    @Bean
    public SecretKey jwtSigningKey() {
        return io.jsonwebtoken.Jwts.SIG.HS256.key().build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return token -> {
            throw new UnsupportedOperationException("JWT decoding is not supported in tests");
        };
    }

}
