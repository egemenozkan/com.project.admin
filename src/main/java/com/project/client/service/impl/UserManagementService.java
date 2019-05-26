package com.project.client.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.client.service.IUserManagementService;
import com.project.common.model.User;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
public class UserManagementService extends BaseApiService implements IUserManagementService {

	@Value("${com.project.api-uri}")
	private String API_URL;

	@Override
	public List<User> getUsers() {
		StringBuffer endpoint = new StringBuffer(API_URL);
		endpoint.append("/api/v1/users");
		return (List<User>) getList(endpoint.toString());
	}

	@Override
	public User getUserById(long id) {
		StringBuffer endpoint = new StringBuffer(API_URL);
		endpoint.append("/api/v1/users/{id}");
		return (User) getObject(endpoint.toString(), User.class, id);
	}

	@Override
	public User saveUser(User user) {
		StringBuffer endpoint = new StringBuffer(API_URL);
		endpoint.append("/api/v1/users");
		return (User) postObject(endpoint.toString(), user, User.class);
	}

}
