package com.project.admin.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;

import com.project.client.service.ItemService;
import com.project.client.service.impl.UserService;
import com.project.common.model.User;

import reactor.core.publisher.Mono;

@Controller
public class MainController {

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@Autowired
	private UserService userService;

	@Autowired
	private ItemService itemService;
	
//	@Autowired
//	private MyApi myApi;
	
//	@Autowired
//	private OAuth2RestTemplate restTemplate;
// 	@GetMapping("/do")
//	public @ResponseBody String doThis(Model model, Authentication authentication) {
// 	      System.out.println("access token: " + restTemplate.getAccessToken());
// 	        String testObject = restTemplate.getForObject("http://localhost:8090/api/do", String.class);
// 	        return testObject + restTemplate.getAccessToken().toString();
// 	}
	@GetMapping("/")
	public RedirectView index(Model model, Authentication authentication) {
		if (authentication instanceof OAuth2AuthenticationToken) {
			OAuth2AuthenticationToken oauth2Authentication = (OAuth2AuthenticationToken) authentication;
			OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(oauth2Authentication.getAuthorizedClientRegistrationId(), authentication.getName());
			model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
		//	myApi.getProfile();
		}

//		 User user = userService.findByUsernameOrEmail(authentication.getName());

//		if (user != null) {
//			model.addAttribute("username", user.getUsername());
//			model.addAttribute("password", user.getPassword());
//			model.addAttribute("role", user.getAuthorities().stream().findAny().orElse(null));
//		}
//
//		String authority = user.getAuthorities().stream().findFirst().get().getAuthority();
//		"ROLE_ADMIN".equals(authority) ? "/admin/index" : "/places/list";
		 RedirectView redirect = new RedirectView("/events/list");
		    redirect.setExposeModelAttributes(false);
		    return redirect;
	}

	@GetMapping("/userinfo")
	public String userinfo(Model model, OAuth2AuthenticationToken authentication) {
		OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
		Map<?, ?> userAttributes = Collections.emptyMap();
		String userInfoEndpointUri = authorizedClient.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();

		if (!StringUtils.isEmpty(userInfoEndpointUri)) {
			userAttributes = WebClient
					.builder()
					.filter(oauth2Credentials(authorizedClient))
					.build()
					.get()
					.uri(userInfoEndpointUri)
					.retrieve()
					.bodyToMono(Map.class)
					.block();
		}

		model.addAttribute("userAttributes", userAttributes);
		itemService.findItem();
		return "/userinfo";
	}

	private ExchangeFilterFunction oauth2Credentials(OAuth2AuthorizedClient authorizedClient) {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			ClientRequest authorizedRequest = ClientRequest
					.from(clientRequest)
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + authorizedClient.getAccessToken().getTokenValue())
					.build();
			return Mono.just(authorizedRequest);
		});
	}

}
