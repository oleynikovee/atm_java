package com.oleynikovee.atm.web.security;

import com.oleynikovee.atm.model.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.annotation.RequestScope;

import java.security.Principal;
import java.util.Optional;

import static com.oleynikovee.atm.web.security.PermittedResources.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final ProprietaryUserDetailsService service;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors()
                .and()
                .authorizeHttpRequests().requestMatchers(SWAGGER_WHITELIST).permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers(INDEX_WHITELIST).permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers(APP_WHITELIST).permitAll()
                .anyRequest()
                .authenticated();
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return service;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new ProprietaryPasswordEncoder();
    }

    @Bean
    @RequestScope
    public Principal getPrincipal() {
        return auth().orElse(null);
    }

    @Bean
    @RequestScope
    public UserPrincipal getRequesterUser(){
        return (UserPrincipal)userDetailsService().loadUserByUsername(getPrincipal().getName());
    }

    /**
     * Return Authentication or empty
     */
    private static Optional<Authentication> auth() {
        return SecurityContextHolder.getContext() == null
                ? Optional.empty()
                : Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }
}