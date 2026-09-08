package com.venkat.bookmyshowapplication.auth.Security;

import com.venkat.bookmyshowapplication.Common.Exceptions.InvalidCredentialsException;
import com.venkat.bookmyshowapplication.User.Model.User;
import com.venkat.bookmyshowapplication.User.Repository.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static String BEARER_PREFIX ="Bearer ";

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public JwtAuthenticationFilter(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);


        if (header==null || !header.startsWith(BEARER_PREFIX)){
            filterChain.doFilter(request,response);
            return;
        }

        String accessToken = getAccessToken(header);

      try {

          long userid = jwtService.extractUserId(accessToken);

          Optional<User> user = userRepository.findById(userid);

          if (user.isEmpty()) {
              throw new InvalidCredentialsException("No user id found");
          }

          if (SecurityContextHolder.getContext().getAuthentication() == null) {


          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                  user.get().getEmail(),
                  null,
                  Collections.emptyList()

          );
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
          filterChain.doFilter(request,response);

      }

       catch ( JwtException
               | IllegalArgumentException
               | InvalidCredentialsException exception){
           SecurityContextHolder.clearContext();
           response.sendError(
                   HttpServletResponse.SC_UNAUTHORIZED,
                   "Invalid or expired access token"
           );

       }



    }

    private String getAccessToken(String header){

        return  header.substring(BEARER_PREFIX.length()).trim();
    }
}
