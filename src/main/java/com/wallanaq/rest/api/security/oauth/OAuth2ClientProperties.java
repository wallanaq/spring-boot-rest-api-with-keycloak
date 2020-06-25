package com.wallanaq.rest.api.security.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "oauth2")
public class OAuth2ClientProperties {
    
    private String[] scopes;

}