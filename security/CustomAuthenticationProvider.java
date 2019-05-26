package com.sevais.online.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sevais.online.enums.UserType;
import com.sevais.opetra.service.db.implementation.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		UserType userType = ((CustomWebAuthenticationDetails) authentication.getDetails()).getUserType();
		AuthUser user = userService.loadUserByUsername(username, userType);

		if (user == null) {
			throw new BadCredentialsException("Username not found.");
		}

		if (!password.equals(user.getPassword())) {
			throw new BadCredentialsException("Wrong password.");
		}
		Collection<GrantedAuthority> allAuthorities = null;
		if (user.getAuthorities() != null) {
			allAuthorities = new HashSet<GrantedAuthority>(user.getAuthorities().size());

			for (Role role : user.getAuthorities())
				allAuthorities.add(role);
		}
		UsernamePasswordAuthenticationToken loggedIn = new UsernamePasswordAuthenticationToken(user, password,
				allAuthorities);
		loggedIn.setDetails(user);
		SecurityContextHolder.getContext().setAuthentication(loggedIn);

		return loggedIn;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
