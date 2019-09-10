package com.project.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.common.model.User;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	IUserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		  User appUser = null;

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
		
				

		// Mapear nuestra lista de Authority con la de spring security
//		List grantList = new ArrayList();
//		for (Authority authority : appUser.getAuthority()) {
//			// ROLE_USER, ROLE_ADMIN,..
//			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
//			grantList.add(grantedAuthority);
//		}

		// Crear El objeto UserDetails que va a ir en sesion y retornarlo.
		UserDetails user = (UserDetails) new User(appUser.getUsername(), appUser.getPassword(), appUser.getRoles());
		return user;
	}
}