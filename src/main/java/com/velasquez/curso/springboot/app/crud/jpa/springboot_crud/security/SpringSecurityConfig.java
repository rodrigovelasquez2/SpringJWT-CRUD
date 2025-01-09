package com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.security;

import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.filter.JWTAuthentication;
import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.filter.JWTValidationFilter;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



import java.util.Arrays;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                        authz -> authz
                                .requestMatchers(HttpMethod.GET, "/api/user").permitAll() //
                                .requestMatchers(HttpMethod.POST, "/api/user/register").permitAll() // USUARIOS NORMALES SEPUEDE REGISTRAR
//                                .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/{id}").hasAnyRole("ADMIN", "USER")
//                                .requestMatchers(HttpMethod.POST, "/api/products").hasRole("ADMIN") // USUARIOS ADMIN PUEDE CREAR USUARIOSq
//                                .requestMatchers(HttpMethod.PUT, "/api/products/{id}").hasRole("ADMIN") // USUARIOS ADMIN PUEDE CREAR ACTUALIZAR
//                                .requestMatchers(HttpMethod.DELETE, "/api/products/{id}").hasRole("ADMIN") // USUARIOS ADMIN PUEDE CREAR ACTUALIZAR
                                .anyRequest().authenticated())
                .addFilter(new JWTAuthentication(authenticationManager()))
                .addFilter(new JWTValidationFilter(authenticationManager()))
                .csrf(config -> config.disable())
                .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

}
