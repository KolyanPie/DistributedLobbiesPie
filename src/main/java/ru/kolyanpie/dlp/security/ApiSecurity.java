package ru.kolyanpie.dlp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(1)
@ConditionalOnProperty(value = "api.auth.enabled", havingValue = "true")
public class ApiSecurity extends WebSecurityConfigurerAdapter {

    @Value("${api.auth.token-header-name}")
    private String principalRequestHeader;

    @Value("${api.auth.token}")
    private String principalRequestValue;

    @Override
    protected void configure(HttpSecurity http) {
        APIKeyAuthFilter filter = new APIKeyAuthFilter(principalRequestHeader);
        filter.setAuthenticationManager(authentication -> {
            String principal = (String) authentication.getPrincipal();
            if (!principalRequestValue.equals(principal)) {
                throw new BadCredentialsException("The API key was not found or not the expected value.");
            }
            authentication.setAuthenticated(true);
            return authentication;
        });
    }
}
