package com.project.client.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.client.service.IUserService;
import com.project.client.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebSecConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private OAuth2LoginSuccessHandler oauth2LoginSuccessHandler;
	
	//Necesario para evitar que la seguridad se aplique a los resources
    //Como los css, imagenes y javascripts
    String[] resources = new String[]{
            "/assets/**","/css/**","/icons/**","/img/**","/js/**","/pages/**"
    };
	
//    @Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//			.antMatchers("/resources/**").permitAll()
//			.antMatchers("/assets/**").permitAll()
//			.antMatchers("/pages/**").permitAll()
//			.antMatchers("/login").permitAll()
//				/* client Crendetial Test */
//			.antMatchers("/do").permitAll()
//			.antMatchers("/admin/**").hasRole("ADMIN")
//			.antMatchers("/places/**").hasAnyRole("USER", "ADMIN")
//			.anyRequest()
//				.authenticated()
//				.and()
//					.oauth2Login().loginPage("/login")
//					.successHandler(oauth2LoginSuccessHandler)
//				.and()
//					.formLogin().loginPage("/login")
//				.and()
//					.headers().frameOptions().sameOrigin()
//				.and()
//					.csrf().disable();
//	}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http.csrf().ignoringAntMatchers("/**").and().authorizeRequests().antMatchers("/api/**").permitAll();

        http
            .authorizeRequests()
			.antMatchers("/resources/**").permitAll()
			.antMatchers("/assets/**").permitAll()
			.antMatchers("/pages/**").permitAll()
			.antMatchers("/login").permitAll()
				/* client Crendetial Test */
			.antMatchers("/do").permitAll()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/places/**").hasAnyRole("USER", "ADMIN")
			.anyRequest()
			.authenticated()
			.and()
				.oauth2Login()
				.loginPage("/login")
				.successHandler(oauth2LoginSuccessHandler)
				.and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .loginProcessingUrl("/plogin").defaultSuccessUrl("/?v=dashboard", true)
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                
            .logout()
                .permitAll()
                .logoutSuccessUrl("/login?logout");
    }
    BCryptPasswordEncoder bCryptPasswordEncoder;
    //Crea el encriptador de contraseñas	
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
//El numero 4 representa que tan fuerte quieres la encriptacion.
//Se puede en un rango entre 4 y 31. 
//Si no pones un numero el programa utilizara uno aleatoriamente cada vez
//que inicies la aplicacion, por lo cual tus contrasenas encriptadas no funcionaran bien
        return bCryptPasswordEncoder;
    }
	
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    
    @Autowired
    IUserService userService;
	
    //Registra el service para usuarios y el encriptador de contrasena
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
 
        // Setting Service to find User in the database.
        // And Setting PassswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());     
    }
    
//    @Bean
//	public GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
//		return (authorities) -> {
//			Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//
//			authorities.forEach(authority -> {
//				String username = "";
//
//				if (OidcUserAuthority.class.isInstance(authority)) {
//					OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
//					OidcIdToken idToken = oidcUserAuthority.getIdToken();
//					username = idToken.getClaimAsString("email");
//				} else if (OAuth2UserAuthority.class.isInstance(authority)) {
//					OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority) authority;
//					Map<String, Object> attributes = oauth2UserAuthority.getAttributes();
//					username = (String) attributes.get("name");
//				}
//				User user = userService.findByUsernameOrEmail(username);
////				if (userService.existsByEmailOrUsername(username)) {
////					User user = userService.findByUsernameOrEmail(username);
////					mappedAuthorities.addAll(user.getAuthorities());
////				} else {
////					mappedAuthorities.add(authority);
////				}
//			});
//
//			return mappedAuthorities;
//		};
//	}

}
