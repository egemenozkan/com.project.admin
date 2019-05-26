package com.project.client.service;

import java.util.List;

import com.project.common.model.User;

public interface IUserManagementService {
	List<User> getUsers();

	User getUserById(long id);

	User saveUser(User user);
}
