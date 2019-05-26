package com.project.client.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.client.model.User;

@Repository
public interface UserDao extends JpaRepository<User, String> {

	public boolean existsByUsername(String username);

	public User findByUsername(String username);

}