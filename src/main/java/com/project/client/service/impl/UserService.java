package com.project.client.service.impl;

import java.net.URI;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.project.client.service.IUserService;
import com.project.common.model.User;

@Service
//@Transactional
public class UserService extends BaseApiService implements IUserService {
	@Value("${com.project.api-uri}")
	private String API_URL;

	@Autowired
	private Logger logger;
	@Autowired
	private Gson gson;
	@SuppressWarnings("unchecked")
	@Override
	public User findByUsernameOrEmail(String usernameOrEmail) {
		StringBuilder strBuilder = new StringBuilder(API_URL).append("/api/v1/users/email?email=")
				.append(usernameOrEmail);
		User user = null;
		try {
			user = (User) this.getObject(strBuilder.toString(), User.class);
		} catch (Exception e) {
			logger.error("::findByUsernameOrEmail {}", e);
		}
		
		
		logger.error("::findByUsernameOrEmail {}", gson.toJson(user));
		return user;
	}

	@Override
	public long registerUser(User user) {
		return (long) this.postObject(API_URL + "/api/v1/users/register", user, Long.class);
	}

	@Override
	public boolean findByFacebookId(String facebookId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsByEmailOrUsername(String usernameOrEmail) {
		URI endpoint = UriComponentsBuilder.fromUriString(API_URL + "/api/v1/users/available/{usernameOrEmail}/")
				.build(usernameOrEmail);
		return (boolean) this.getObject(endpoint.toString(), Boolean.class);
	}

	@Override
	public User updateSocialUserByEmail(User user) {
		return (User) postObject(API_URL + "/api/v1/users/social", user, User.class);
	}


}
