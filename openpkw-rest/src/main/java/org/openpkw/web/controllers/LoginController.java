package org.openpkw.web.controllers;

import org.openpkw.web.security.JwtTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;
import java.util.Map;

/**
 * Login endpoint that authenticates users and issues JWT tokens.
 * Replaces the old OAuth2 password grant at /api/login.
 */
@RestController
public class LoginController {

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private JwtTokenService jwtTokenService;

    @RequestMapping(value = "/api/login", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()));

            String accessToken = jwtTokenService.generateAccessToken(authentication);
            String refreshToken = jwtTokenService.generateRefreshToken(authentication);

            return ResponseEntity.ok(Map.of(
                    "access_token", accessToken,
                    "refresh_token", refreshToken,
                    "token_type", "Bearer"
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }

    public record LoginRequest(String username, String password) {}
}
