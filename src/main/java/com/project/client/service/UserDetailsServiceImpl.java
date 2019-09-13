package com.project.client.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.common.model.User;
import com.project.common.model.UserPrincipal;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	IUserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User appUser = null;
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		// Buscar el usuario con el repositorio y si no existe lanzar una exepcion
		try {
			appUser = userService.findByUsernameOrEmail(username);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("--");
		}

		if (appUser == null) {
			throw new UsernameNotFoundException(username + " bulunamadı!.");
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		if (appUser.getRoles() != null) {
			appUser.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
		}

		return new UserPrincipal(appUser.getId(), appUser.getFirstName(), appUser.getLastName(),
				appUser.getEmail(), appUser.getPictureUrl(), appUser.getUsername(), appUser.getPassword(), enabled,
				accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
}