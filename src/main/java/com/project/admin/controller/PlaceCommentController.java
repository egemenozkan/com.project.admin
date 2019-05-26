package com.project.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.api.data.enums.Language;
import com.project.api.data.enums.MainType;
import com.project.api.data.enums.PlaceType;
import com.project.api.data.enums.Status;
import com.project.api.data.model.comment.Comment;
import com.project.api.data.model.comment.PlaceComment;
import com.project.api.data.model.user.CommentUser;
import com.project.client.service.ICommentManagementService;
import com.project.client.service.IDatapoolService;

@Controller
@RequestMapping(value = "/places")
public class PlaceCommentController {

	Logger LOG = LoggerFactory.getLogger(PlaceCommentController.class);

	private final static List<String> COUNTRIES = new ArrayList<String>() {
		{
			add("Turkey");
		}
	};

	private final static MainType[] PLACE_MAIN_TYPES = MainType.values();
	private final static PlaceType[] PLACE_TYPES = PlaceType.values();
	private final static Language[] LANGUAGES = Language.values();
	private final static int COUNTRY_ID = 223;

	@Autowired
	private ICommentManagementService commentManagementService;

	@Autowired
	private IDatapoolService datapoolService;

	
	
	@PostMapping({ "/comments/{id}/status" })
	public @ResponseBody String updatePlaceCommentStatus(@PathVariable long id, @RequestParam Status status) {
		commentManagementService.updatePlaceCommentStatus(id, status);
		return "true";
		
	}
	@GetMapping({ "/comments/list" })
	public String placesComments(Model model) {
		Status[] statuses = Status.values();
		
		model.addAttribute("title", "Place Comments");
		List<PlaceComment> placeComments = commentManagementService.getPlaceComments(Language.RUSSIAN);
		model.addAttribute("comments", placeComments);
		model.addAttribute("statuses", statuses);

		return "comments/places_list";
	}
	

	
	@GetMapping({ "/{id}/comments" })
	public String placesComments(@PathVariable long id) {
		Comment comment = new Comment();
		comment.setLanguage(Language.TURKISH);
		comment.setMessage("TEst Başarılı");
		CommentUser user = new CommentUser();
		user.setId(1);
		comment.setUser(user);
		commentManagementService.savePlaceComment(comment, id);

		return "comments/places_list";
	}
	
	
	

}
