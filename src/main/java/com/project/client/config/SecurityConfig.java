package com.project.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.project.client.security2.CustomAccessDeniedHandler;
import com.project.client.security2.CustomAuthenticationProvider;
import com.project.client.security2.CustomLogoutSuccessHandler;
import com.project.client.security2.CustomWebAuthenticationDetailsSource;

//@Configuration
//// @ImportResource({ "classpath:webSecurityConfig.xml" })
//@EnableWebSecurity
//@Profile("!https")
//@Order(Ordered.LOWEST_PRECEDENCE)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public SecurityConfig() {
		super();
	}

	@Autowired
	private CustomAuthenticationProvider authProvider;
	@Autowired
	private CustomWebAuthenticationDetailsSource authDetailsSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(authProvider);

		// @formatter:off
		// auth.inMemoryAuthentication()
		// .withUser("user1").password("user1Pass").roles("USER")
		// .and()
		// .withUser("user2").password("user2Pass").roles("USER")
		// .and()
		// .withUser("admin").password("adminPass").roles("ADMIN");
		// @formatter:on
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		// @formatter:off
		http.csrf().ignoringAntMatchers("/**").and().authorizeRequests().antMatchers("/api/**").permitAll();

		http.authorizeRequests()
				// .antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/anonymous*").anonymous()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/pages/**").permitAll()
				.antMatchers("/assets/**").permitAll()
				.antMatchers("/login*").permitAll()
				.anyRequest().authenticated()
				.and()
					.formLogin().authenticationDetailsSource(authDetailsSource)
					.loginPage("/login")
				.loginProcessingUrl("/perform_login").defaultSuccessUrl("/?v=dashboard", true)
				.failureUrl("/login?error=true").and().logout().logoutUrl("/logout").logoutSuccessUrl("login?logout")
				.deleteCookies("JSESSIONID").logoutSuccessHandler(logoutSuccessHandler());
		// .and()
		// .exceptionHandling().accessDeniedPage("/accessDenied");
		// .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
		// @formatter:on
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new CustomLogoutSuccessHandler();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

}