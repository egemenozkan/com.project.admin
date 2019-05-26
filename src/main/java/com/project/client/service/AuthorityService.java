package com.project.client.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.client.dao.AuthorityDao;
import com.project.client.model.Authority;

@Service
@Transactional
public class AuthorityService {

	@Autowired
	private AuthorityDao authorityDao;

	public Authority findByAuthority(String authority) {
		return authorityDao.findByAuthority(authority);
	}

}
