package com.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//@EnableTransactionManagement
@ComponentScan(basePackages = "com.project")
//@EnableJpaRepositories(basePackages = "com.project.client.dao")
//@EntityScan(basePackages = "com.project.client.model")
public class Application {

	@Autowired
	private Logger log;
	//

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	@Scope("prototype")
	public Logger log(InjectionPoint ip) {
		return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
	}

	
	
	@Bean
	RestTemplate restTemplate(OAuth2AuthorizedClientService clientService) {
		return new RestTemplateBuilder()
				.interceptors((ClientHttpRequestInterceptor) (httpRequest, bytes, execution) -> {

					OAuth2AuthenticationToken token = OAuth2AuthenticationToken.class
							.cast(SecurityContextHolder.getContext().getAuthentication());

					OAuth2AuthorizedClient client = clientService
							.loadAuthorizedClient(token.getAuthorizedClientRegistrationId(), token.getName());

					httpRequest.getHeaders().add(HttpHeaders.AUTHORIZATION,
							"Bearer " + client.getAccessToken().getTokenValue());

					return execution.execute(httpRequest, bytes);
				}).build();
	}

}
