package com.project.client.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project.client.service.impl.UserService;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private UserService userService;



	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		String username = authentication.getName();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//		if (!userService.existsByEmailOrUsername(username)) {
//			String authorityName = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
//					.findFirst().get();
////			Authority authority = authorityService.findByAuthority(authorityName);
////			Set<Authority> authorities = Stream.of(authority).collect(Collectors.toSet());
////			userService.save(new User(username, "", authorities));
//		}

		super.onAuthenticationSuccess(request, response, authentication);
	}

}
