package com.project.admin.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.project.client.service.IUserService;
import com.project.common.model.User;

@Controller
public class LoginController {
	@Autowired
	private IUserService userService;
	@Autowired
	private InMemoryClientRegistrationRepository clientRegistrationRepository;
	@Autowired
	private Logger logger;
	@Autowired
	private Gson gson;


	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@ModelAttribute(name = "clientRegistrationList")
	public List<ClientRegistration> getClientRegistrationList() {
		List<ClientRegistration> clientRegistrationList = new ArrayList<>();
		clientRegistrationRepository.iterator().forEachRemaining(clientRegistrationList::add);
		clientRegistrationList.sort(Comparator.comparing(ClientRegistration::getClientName));
		return clientRegistrationList;
	}

	@PostMapping("/pLogin")
	public String postLogin(@RequestParam String username, @RequestParam String password) {

		if (username == null || username.isBlank()) {
			return "error";
		}

		if (password == null || password.isBlank()) {
			return "error";
		}

		 User user = userService.findByUsernameOrEmail(username);

		if (user != null) {
			if (user.getPassword().equals(password)) {
				logger.debug("user: {}", gson.toJson(user));
			}
		}

		return "login";
	}

}
