package com.project.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.api.data.enums.Language;
import com.project.api.data.enums.MainType;
import com.project.api.data.enums.PlaceType;
import com.project.api.data.model.place.Place;
import com.project.client.service.IDatapoolService;
import com.project.client.service.IUserManagementService;
import com.project.common.model.User;

@Controller
@RequestMapping(value = "/users/")
public class UserController {

	Logger LOG = LoggerFactory.getLogger(UserController.class);

	private static final List<String> COUNTRIES = new ArrayList<>() {
		{
			add("Turkey");
		}
	};

	private static final  MainType[] PLACE_MAIN_TYPES = MainType.values();
	private static final  PlaceType[] PLACE_TYPES = PlaceType.values();
	private static final  Language[] LANGUAGES = Language.values();
	private static final  int COUNTRY_ID = 223;

	@Autowired
	private IUserManagementService userManagementService;

	@Autowired
	private IDatapoolService datapoolService;

	@GetMapping({ "/list" })
	public String listPlaces(Model model) {
		model.addAttribute("title", "Users");
		List<User> users = userManagementService.getUsers();
		model.addAttribute("users", users);

		return "users/list";
	}

	@GetMapping("/editor/{id}")
	public String editorUser(Model model, @PathVariable long id) {
		User user = userManagementService.getUserById(id);
		model.addAttribute("user", user);
		return "users/editor";
	}

	@GetMapping("/editor")
	public String editorNewPlace(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "users/editor";
	}

	@PostMapping(value = "/savep")
	public String savePlace(@ModelAttribute("place") Place place) {
		// Place result = placeService.savePlace(place);
		// if (result != null) {
		// return "redirect:/places/editor/" + result.getId();
		// }
		return "redirect:/places/editor";
	}

	/** Landingpage **/

	@GetMapping("/{id}/pages/{language}")
	public String editorLandingPage(Model model, @PathVariable long id, @PathVariable String language) {
		// model.addAttribute("languages", LANGUAGES);
		// model.addAttribute("placeMainTypes", PLACE_MAIN_TYPES);
		// model.addAttribute("placeTypes", PLACE_TYPES);
		//
		// if (language != null) {
		// language = language.toUpperCase();
		// }
		//
		// LandingPage page = placeService.getLandingPageByPlaceIdAndLanguage(id,
		// language);
		// /** Create New Page **/
		// if (page == null) {
		// page = new LandingPage();
		// page.setLanguage(Language.getByCode(language));
		// Place place = placeService.getPlaceByIdAndLanguage(id, language);
		//
		// if (place == null) {
		// LOG.warn("::editorLandingPage place is null for {}", id);
		// }
		// page.setTitle(place.getName());
		// page.setPlace(place);
		// Content content = new Content();
		// List<Content> contents = new ArrayList<Content>();
		// contents.add(content);
		// page.setContents(contents);
		//
		// }
		// model.addAttribute("page", page);

		return "places/page_editor";
	}

}
