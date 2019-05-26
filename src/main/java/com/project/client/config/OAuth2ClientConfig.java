package com.project.client.config;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;


//@Configuration
//@EnableOAuth2Client
public class OAuth2ClientConfig {
    @Value("${spring.security.oauth2.client.registration.myserver.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.myserver.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.provider.myserver.token-uri}")
    private String accessTokenUri;

    @Value("${spring.security.oauth2.client.provider.myserver.authorization-uri}")
    private String userAuthorizationUri;

    @Value("${spring.security.oauth2.client.registration.myserver.redirect-uri-template}")
    private String redirectUri;

    @Bean
    public OAuth2ProtectedResourceDetails resourceDetails() {
        final AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        details.setAccessTokenUri(accessTokenUri);
        details.setUserAuthorizationUri(userAuthorizationUri);
        details.setScope(Arrays.asList("places"));
        details.setPreEstablishedRedirectUri(redirectUri);
        details.setUseCurrentUri(false);
        return details;
    }

    @Bean
    public OAuth2RestTemplate restTemplate(final OAuth2ClientContext clientContext) {
        final OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails(), clientContext);
        template.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        return template;
    }

}
