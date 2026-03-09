package org.openpkw.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.inject.Inject;

/**
 * Spring Security configuration
 * @author Sebastian Pogorzelski
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan("org.openpkw.web.security")
public class SecurityConfig {

    @Inject
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/scripts/**/*.js", "/scripts/**/*.html")
                .requestMatchers("/bower_components/**")
                .requestMatchers("/i18n/**")
                .requestMatchers("/assets/**")
                .requestMatchers("/swagger-ui/index.html")
                .requestMatchers("/api/register")
                .requestMatchers("/api/activate")
                .requestMatchers("/api/account/reset_password/init")
                .requestMatchers("/api/login")
                .requestMatchers("/api/account/reset_password/finish")
                .requestMatchers("/test/**");
    }

}
