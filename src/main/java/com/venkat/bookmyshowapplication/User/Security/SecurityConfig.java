package com.venkat.bookmyshowapplication.User.Security;

import com.venkat.bookmyshowapplication.User.Repository.UserRepository;
import com.venkat.bookmyshowapplication.auth.Security.JwtAuthenticationFilter;
import com.venkat.bookmyshowapplication.auth.Security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            UserRepository userRepository,
            JwtService jwtService
    ) {
        return new JwtAuthenticationFilter(
                userRepository,
                jwtService
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // Public APIs
                        .requestMatchers(
                                "/user/Register",
                                "/auth/login",
                                "/auth/refresh",
                                "/auth/logout",

                                // OAuth entry + callback endpoints
                                "/oauth2/**",
                                "/login/oauth2/**"
                        ).permitAll()

                        // Role/permission protected APIs
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/shows/create")
                        .hasAuthority("SHOW_CREATE")

                        // Everything else requires authentication
                        .anyRequest()
                        .authenticated()
                )

                // Existing JWT-based application remains stateless
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // Google OAuth2 Login
                .oauth2Login(Customizer.withDefaults())

                // Existing JWT filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}