package com.wallanaq.rest.api.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.wallanaq.rest.api.security.oauth.OAuth2ClientProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

@Configuration
public class SwaggerConfig {

    private static final String OAUTH_NAME = "oauth2-spring-keycloak";

	@Value("${keycloak.auth-server-url}")
	private String authServer;

	@Value("${keycloak.resource}")
	private String clientId;

	@Value("${keycloak.credentials.secret}")
	private String secret;

	@Value("${keycloak.realm}")
    private String realm;
    
    @Value("${api.info.name}")
    private String apiName;
    
    @Autowired
    private OAuth2ClientProperties oauth2Props;

    @Bean
	public Docket api() {	
		return new Docket(DocumentationType.SWAGGER_2).select()
								                      .apis(RequestHandlerSelectors.any())
								                      .paths(PathSelectors.any())
								                      .build()
								                      .securitySchemes(Arrays.asList(securityScheme()))
								                      .securityContexts(Arrays.asList(securityContext()));
    }
    
	@Bean
	public SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder()
										   .realm(this.realm)
										   .clientId(this.clientId)
										   .clientSecret(secret)
										   .appName(this.apiName)
										   .scopeSeparator(" ")
										   .build();
    }
    
	private SecurityScheme securityScheme() {
		String urlAuthServer = String.format("%s/realms/%s/protocol/openid-connect", this.authServer, this.realm);
		GrantType grantType = new AuthorizationCodeGrantBuilder()
									.tokenEndpoint(new TokenEndpoint(urlAuthServer + "/token", this.apiName))
									.tokenRequestEndpoint(new TokenRequestEndpoint(urlAuthServer + "/auth", this.clientId, null))
									.build();
		return new OAuthBuilder().name(OAUTH_NAME).grantTypes(Arrays.asList(grantType)).build();
	}

	private List<AuthorizationScope> scopes() {
        String[] scopes = this.oauth2Props.getScopes();
		if(scopes != null) {
			return Stream.of(scopes)
						 .map(scope -> new AuthorizationScope(scope, ""))
						 .collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
	
	private SecurityContext securityContext() {
		AuthorizationScope[] scopes = scopes().toArray(new AuthorizationScope[scopes().size()]);
		return SecurityContext.builder()
							  .securityReferences(Arrays.asList(new SecurityReference(OAUTH_NAME, scopes)))
							  .forPaths(PathSelectors.any())
							  .build();
	}
    
}