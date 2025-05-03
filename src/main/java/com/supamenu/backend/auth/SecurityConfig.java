package com.supamenu.backend.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.http.HttpHeaders.*;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Slf4j
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SecurityExceptionHandler securityExceptionHandler;


    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/actuator/**",
            "/auth/login",
            "/api/v1/auth/refresh",
            "/api/v1/auth/register",
            "/api/v1/auth/verify-account",
            "/api/v1/auth/initiate-password-reset",
            "/api/v1/auth/reset-password",
            "/api/v1/payment",
            "/api/v1/restaurants",
            "/files/**"
    };

    @Bean
     UrlBasedCorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.setAllowedOriginPatterns(List.of("http://localhost:8080", "http://127.0.0.1:8080", "10.12.73.211:8080")); // Allow all origins
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of(
                ORIGIN,
                CONTENT_TYPE,
                ACCEPT,
                AUTHORIZATION
        ));
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(
                        c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(registry ->
                        registry
                                .requestMatchers(AUTH_WHITELIST).permitAll()
                                .requestMatchers(HttpMethod.POST, "/users").permitAll()

                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/refresh").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/restaurants").hasRole("ADMIN") // -> ROLE_ADMIN
                                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(c -> {
                    c.authenticationEntryPoint(securityExceptionHandler);
                    c.accessDeniedHandler(securityExceptionHandler);
                });

        return http.build();
    }
}