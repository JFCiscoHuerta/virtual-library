package com.gklyphon.VirtualLibrary.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

/**
 * Security configuration class for the application.
 * Defines authorization policies and CORS configuration.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 19-Oct-2024
 */
@Configuration
public class SecurityConfig {

    /**
     * Configures the authorization rules for HTTP requests.
     *
     * <p>Allows public access to specific GET routes and restricts POST and DELETE
     * routes to users with the ADMIN role.</p>
     *
     * @param http the {@link HttpSecurity} object used to customize HTTP security.
     * @return the configured {@link SecurityFilterChain} instance.
     * @throws Exception if an error occurs during security configuration.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (auths) -> auths
                        .requestMatchers(HttpMethod.GET, "/v1/books", "/v1/books/{id}",
                                "/v1/books/by-title", "/v1/books/by-isbn").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/books/save-book").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/v1/books/update-book/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/v1/books/delete-book/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/v1/authors", "/v1/authors/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/authors/save-author").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/v1/authors/update-author/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/v1/authors/delete-author/{id}").permitAll()
                        .anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
        ;
        return http.build();
    }

    /**
     * Configures Cross-Origin Resource Sharing (CORS) for the application.
     *
     * <p>Allows requests from any origin with various HTTP methods and authorized headers.
     * Enables the use of credentials in requests.</p>
     *
     * @return the configured {@link CorsConfigurationSource} instance.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
        configuration.setAllowedHeaders(List.of("Content-Type","Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
