package com.project.admin.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.api.data.enums.LandingPageType;
import com.project.api.data.enums.MainType;
import com.project.api.data.enums.PeriodType;
import com.project.api.data.enums.PlaceType;
import com.project.api.data.model.common.Address;
import com.project.api.data.model.event.TimeTable;
import com.project.api.data.model.file.MyFile;
import com.project.api.data.model.place.Place;
import com.project.api.data.model.place.PlaceLandingPage;
import com.project.client.service.IDatapoolService;
import com.project.client.service.IFileService;
import com.project.client.service.IPlaceService;
import com.project.common.model.City;
import com.project.common.model.Region;
import com.project.common.model.Subregion;

@Controller
@RequestMapping(value = "/places/")
public class PlaceController {

	Logger LOG = LoggerFactory.getLogger(PlaceController.class);

	private static final List<String> COUNTRIES = new ArrayList<>() {
		{
			add("Turkey");
		}
	};

	private static final int COUNTRY_ID = 223;

	@Autowired
	private IPlaceService placeService;

	@Autowired
	private IDatapoolService datapoolService;

	@Autowired
	private IFileService fileService;
	
	private static final PeriodType[] PERIOD_TYPES = PeriodType.values();


	@GetMapping({ "/list", "/list/{mainType}", "/list/{mainType}/{type}" })
	public String listPlaces(Model model, @PathVariable(required = false) String mainType, @PathVariable(required = false) String type) {
		model.addAttribute("title", "Places");
		List<Place> places = null;
		if (mainType == null && type == null) {
			places = placeService.getPlaces();
		}
		if (mainType != null && type == null) {
			mainType = mainType.toUpperCase();
			places = placeService.getPlacesByMainType(MainType.valueOf(mainType));
		}
		if (mainType != null && type != null) {
			type = type.toUpperCase();
			places = placeService.getPlacesByType(PlaceType.valueOf(type));
		}
		model.addAttribute("places", places);

		return "places/list";
	}

	@GetMapping("/editor/{id}")
	public String editorPlace(Model model, @PathVariable long id) {

		List<City> cities = datapoolService.getCitiesByCountryId(COUNTRY_ID);
		model.addAttribute("cities", cities);
		model.addAttribute("title", "Place Editor");
		model.addAttribute("id", id);
		Place place = placeService.getPlaceById(id);
		if (place != null && place.getAddress() != null) {
			List<Region> regions = datapoolService.getRegionsByCityId(place.getAddress().getCityId());
			List<Subregion> subregions = datapoolService.getSubregionsByRegionId(place.getAddress().getRegionId());
			model.addAttribute("regions", regions);
			model.addAttribute("subregions", subregions);
		}

		model.addAttribute("place", place);
		model.addAttribute("periodTypes", PERIOD_TYPES);

		return "places/editor";
	}

	@GetMapping("/editor")
	public String editorNewPlace(Model model) {

		List<City> cities = datapoolService.getCitiesByCountryId(COUNTRY_ID);
		model.addAttribute("cities", cities);

		model.addAttribute("title", "Place Editor");
		Place place = new Place();
		place.setAddress(new Address());
		model.addAttribute("place", place);
		model.addAttribute("periodTypes", PERIOD_TYPES);

		return "places/editor";
	}

	@PostMapping(value = "/savep")
	public String savePlace(@ModelAttribute("place") Place place) {
		Place result = placeService.savePlace(place);
		if (result != null) {
			return "redirect:/places/editor/" + result.getId();
		}
		return "redirect:/places/editor";
	}

	/** Landingpage **/

	@GetMapping("/{id}/pages/{language}")
	public String editorLandingPage(Model model, @PathVariable long id, @PathVariable String language) {

		if (language != null) {
			language = language.toUpperCase();
		}

		PlaceLandingPage page = placeService.getLandingPageByPlaceIdAndLanguage(id, language);
		model.addAttribute("page", page);

		return "places/page_editor";
	}

	@PostMapping("/pages")
	public String savePage(Model model, @ModelAttribute("page") PlaceLandingPage page, BindingResult result) {
		placeService.saveLandingPage(page);

		return "redirect:/places/" + page.getPlace().getId() + "/pages/" + page.getLanguage().getCode().toLowerCase();

	}

	@GetMapping("/autocomplete")
	public @ResponseBody List<Place> autocompletePlaces(Model model, @RequestParam String query) {
		if (query != null && query.length() < 3) {
			return Collections.emptyList();
		}
		return placeService.autocompletePlaces(query);

	}

	@GetMapping("/multimedia/{id}")
	public String placeMultimedia(Model model, @PathVariable long id) {
		Place place = placeService.getPlaceById(id);
		model.addAttribute("place", place);
		List<MyFile> files = fileService.getFilesByPageId(LandingPageType.PLACE.getId(), id);
		model.addAttribute("files", files);
		return "places/multimedia_editor";
	}
	
	@PostMapping({ "/{id}/main-image" })
	public @ResponseBody boolean setMainImage(Model model, @PathVariable long id, @RequestParam long fileId) {
		placeService.setMainImage(id, fileId);
		return true;
	}
	
	
	@GetMapping("/{id}/time-table")
	public @ResponseBody List<TimeTable> getTimeTable(@PathVariable long id) {
		return placeService.getTimeTableByPlaceId(id);
	}
	
	@PostMapping("/time-table")
	public @ResponseBody TimeTable saveTimeTable(RequestEntity<TimeTable> requestEntity) {
		placeService.saveTimeTable(requestEntity.getBody());
		return requestEntity.getBody();
	}
	
	@DeleteMapping("/time-table/{id}")
	public @ResponseBody boolean deleteTimeTable(@PathVariable long id) {
		placeService.deleteTimeTableById(id);
		return true;
	}
	

	
	

}
