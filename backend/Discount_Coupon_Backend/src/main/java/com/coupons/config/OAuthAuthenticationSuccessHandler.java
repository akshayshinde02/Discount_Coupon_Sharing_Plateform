package com.coupons.config;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.coupons.enums.AccountStatus;
import com.coupons.enums.OathProvider;
import com.coupons.enums.UserRole;
import com.coupons.models.User;
import com.coupons.models.UserToken;
import com.coupons.repositories.TokenRepository;
import com.coupons.repositories.UserRepository;

import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");

        // identify the provider

        var oauth2AuthenicationToken = (OAuth2AuthenticationToken) authentication;

        String authorizedClientRegistrationId = oauth2AuthenicationToken.getAuthorizedClientRegistrationId();

        logger.info(authorizedClientRegistrationId);

        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(authorizedClientRegistrationId, oauth2AuthenicationToken.getName());

        String accessToken="";

        if (authorizedClient != null) {
            accessToken = authorizedClient.getAccessToken().getTokenValue();
            // System.out.println("Access Token : "+accessToken);

        }else{

            // System.out.println("No authorized client found!");

        }

        oauthUser.getAttributes().forEach((key, value) -> {
            logger.info(key + " : " + value);
        });

        User user = new User();
        user.setRole(UserRole.USER);
        user.setIsverified(true);
        user.setPassword("dummy");

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {

            // google
            // google attributes

            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setFirstName(oauthUser.getAttribute("given_name").toString());
            user.setLastName(oauthUser.getAttribute("family_name").toString());
            // user.setProfilePicture(oauthUser.getAttribute("picture"));
            user.setOathProvider(OathProvider.GOOGLE);
            user.setRole(UserRole.USER);
            user.setAccountStatus(AccountStatus.ACTIVE);
            user.setDeleted(false);
            user.setCreatedAt(LocalDateTime.now());


            

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {

           
        }

        else if (authorizedClientRegistrationId.equalsIgnoreCase("linkedin")) {

        }

        else {
            logger.info("OAuthAuthenicationSuccessHandler: Unknown provider");
        }

        

        User user2 = userRepo.findByEmail(user.getEmail());
        if (user2 == null) {
            userRepo.save(user);
            // System.out.println("user saved:" + user.getEmail());
        }

        if(user2 == null){
        var toks = UserToken.builder()
            .user(user)
            .token(accessToken)
            .expired(false)
            .build();
            tokenRepository.save(toks);
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "http://localhost:8080/login/oauth2/code/google\r\n" + //
                        "");

    }

}