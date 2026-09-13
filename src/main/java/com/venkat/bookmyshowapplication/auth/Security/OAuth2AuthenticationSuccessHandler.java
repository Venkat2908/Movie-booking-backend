package com.venkat.bookmyshowapplication.auth.Security;


import com.venkat.bookmyshowapplication.User.Dto.TokenGenerateDto;
import com.venkat.bookmyshowapplication.User.Model.*;
import com.venkat.bookmyshowapplication.User.Repository.RoleRepository;
import com.venkat.bookmyshowapplication.User.Repository.UserAuthProviderRespository;
import com.venkat.bookmyshowapplication.User.Repository.UserRepository;
import com.venkat.bookmyshowapplication.auth.model.TokenResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Date;

@Component
public class OAuth2AuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final UserAuthProviderRespository userAuthProviderRepository;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    public OAuth2AuthenticationSuccessHandler(
            UserRepository userRepository,
            UserAuthProviderRespository userAuthProviderRepository,
            RoleRepository roleRepository,
            TokenService tokenService,
            ObjectMapper objectMapper
    ) {
        this.userRepository = userRepository;
        this.userAuthProviderRepository = userAuthProviderRepository;
        this.roleRepository = roleRepository;
        this.tokenService = tokenService;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();
        String googleUserId = oidcUser.getSubject();

        if (email == null || googleUserId == null) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Google account information is incomplete"
            );
            return;
        }

        User user = userRepository.findByEmail(email)
                .orElseGet(() ->
                        createGoogleUser(name, email)
                );

        linkGoogleProvider(user, googleUserId);

        TokenGenerateDto tokenGenerateDto = new TokenGenerateDto();
        tokenGenerateDto.setUser(user);
        tokenGenerateDto.setSubsequent(false);

        TokenResponse tokenResponse =
                tokenService.issueTokens(tokenGenerateDto);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        objectMapper.writeValue(
                response.getWriter(),
                tokenResponse
        );
    }

    private User createGoogleUser(
            String name,
            String email
    ) {

        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Default USER role is not configured"
                        )
                );

        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setVerified(true);
        user.setStatus(UserResponseStatus.ACTIVE);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        user.getRoles().add(userRole);

        return userRepository.save(user);
    }

    private void linkGoogleProvider(
            User user,
            String googleUserId
    ) {

        boolean googleAlreadyLinked =
                userAuthProviderRepository.existsByUserAndProvider(
                        user,
                        Authprovider.GOOGLE
                );

        if (googleAlreadyLinked) {
            return;
        }

        UserAuthProvider provider =
                new UserAuthProvider();

        provider.setUser(user);
        provider.setProvider(Authprovider.GOOGLE);
        provider.setProviderUserId(googleUserId);
        provider.setCreatedAt(new Date());
        provider.setUpdatedAt(new Date());

        userAuthProviderRepository.save(provider);
    }
}