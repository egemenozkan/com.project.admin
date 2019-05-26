package com.project.client.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.client.model.Authority;

@Repository
public interface AuthorityDao extends JpaRepository<Authority, String> {

	public Authority findByAuthority(String authority);

}
