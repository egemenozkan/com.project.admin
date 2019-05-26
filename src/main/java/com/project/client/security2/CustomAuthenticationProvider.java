package com.project.client.security2;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

//	@Autowired
//	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
//		UserType userType = ((CustomWebAuthenticationDetails) authentication.getDetails()).getUserType();
//		AuthUser user = userService.loadUserByUsername(username, userType);
//
//		if (user == null) {
//			throw new BadCredentialsException("Username not found.");
//		}
//
//		if (!password.equals(user.getPassword())) {
//			throw new BadCredentialsException("Wrong password.");
//		}
//		Collection<GrantedAuthority> allAuthorities = null;
//		if (user.getAuthorities() != null) {
//			allAuthorities = new HashSet<GrantedAuthority>(user.getAuthorities().size());
//
//			for (Role role : user.getAuthorities())
//				allAuthorities.add(role);
//		}
//		UsernamePasswordAuthenticationToken loggedIn = new UsernamePasswordAuthenticationToken(user, password,
//				allAuthorities);
//		loggedIn.setDetails(user);
//		SecurityContextHolder.getContext().setAuthentication(loggedIn);

		return null;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
