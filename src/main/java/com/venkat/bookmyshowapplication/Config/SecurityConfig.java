package com.venkat.bookmyshowapplication.Config;

import com.venkat.bookmyshowapplication.User.Repository.UserRepository;
import com.venkat.bookmyshowapplication.auth.Security.JwtAuthenticationFilter;
import com.venkat.bookmyshowapplication.auth.Security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            UserRepository userRepository,
            JwtService jwtService
    ) {
        return new JwtAuthenticationFilter(userRepository, jwtService);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {


        http.csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/Register", "/auth/login","/auth/refresh","/auth/logout").permitAll()

                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/shows/create").hasAuthority("SHOW_CREATE")
                        .anyRequest().authenticated()

                ).sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ) .addFilterBefore(
                        jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );
        return http.build();
    }
}
