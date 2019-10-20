package com.project.admin.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.project.api.data.enums.LandingPageType;
import com.project.api.data.enums.MainType;
import com.project.api.data.enums.PeriodType;
import com.project.api.data.enums.PlaceType;
import com.project.api.data.model.common.Address;
import com.project.api.data.model.event.TimeTable;
import com.project.api.data.model.file.MyFile;
import com.project.api.data.model.gis.City;
import com.project.api.data.model.gis.District;
import com.project.api.data.model.place.Place;
import com.project.api.data.model.place.PlaceLandingPage;
import com.project.api.data.model.place.PlaceRequest;
import com.project.client.service.IDatapoolService;
import com.project.client.service.IFileService;
import com.project.client.service.IPlaceService;

@Controller
@RequestMapping(value = "/places/")
public class PlaceController {

	Logger logger = LoggerFactory.getLogger(PlaceController.class);

	private static final int TURKEY_COUNTRY_ID = 1;

	@Autowired
	private IPlaceService placeService;

	@Autowired
	private IDatapoolService datapoolService;

	@Autowired
	private IFileService fileService;

	private static final PeriodType[] PERIOD_TYPES = PeriodType.values();

	@GetMapping({"/list/{mainType}", "/list/{mainType}/{type}" })
	public String listPlaces(Model model, @RequestParam(defaultValue = "false") boolean filter, @PathVariable(required = false) String mainType,
			@PathVariable(required = false) String type) {
		
		/* Filters */
		model.addAttribute("cities", datapoolService.getCitiesByCountry(TURKEY_COUNTRY_ID));
		model.addAttribute("placeTypes", PlaceType.values());
		/* End Of Filters */
		
		PlaceRequest placeRequest = new PlaceRequest();
		if (mainType != null && type == null) {
			mainType = mainType.toUpperCase();
			placeRequest.setMainType(MainType.valueOf(mainType));
			filter = true;
		}
		if (mainType != null && type != null) {
			type = type.toUpperCase();
			placeRequest.setType(PlaceType.valueOf(type));
			filter = true;
		}

		placeRequest.setHideAddress(Boolean.TRUE);
		placeRequest.setHideContact(Boolean.TRUE);
		placeRequest.setHideContent(Boolean.TRUE);
		placeRequest.setHideImages(Boolean.TRUE);
		placeRequest.setHideMainImage(Boolean.TRUE);

		model.addAttribute("places", filter ? placeService.getPlaces(placeRequest) : Collections.emptyList());
		model.addAttribute("placeRequest", placeRequest);
		
		return "places/list";
	}
	
	
	@GetMapping({"/list"})
	public String filterPlaces(Model model, @RequestParam(defaultValue = "false") boolean filter,
			@RequestParam(required = false, defaultValue = "0") int  mainType,
			@RequestParam(required = false, defaultValue = "0") int type,
			@RequestParam(required = false, defaultValue = "0") int city,
			@RequestParam(required = false, defaultValue = "0") int district) {
		
		/* Filters */
		List<City> cities = datapoolService.getCitiesByCountry(TURKEY_COUNTRY_ID);
		List<District> districts = Collections.emptyList();
		if (!CollectionUtils.isEmpty(cities)) {
			 districts = datapoolService.getDistrictsByCity(city > 0 ? city : cities.get(0).getId());
		}
		model.addAttribute("cities", cities);
		model.addAttribute("districts", districts);
		model.addAttribute("placeTypes", PlaceType.values());
		/* End Of Filters */
		
		PlaceRequest placeRequest = new PlaceRequest();
		if (mainType > 0) {
			placeRequest.setMainType(MainType.getById(mainType));
		}
		if (type > 0) {
			placeRequest.setType(PlaceType.getById(type));
		}
		if (city > 0) {
			placeRequest.setCityId(city);
		}
		if (district > 0) {
			placeRequest.setDistrictId(district);
		}

		placeRequest.setHideAddress(Boolean.TRUE);
		placeRequest.setHideContact(Boolean.TRUE);
		placeRequest.setHideContent(Boolean.TRUE);
		placeRequest.setHideImages(Boolean.TRUE);
		placeRequest.setHideMainImage(Boolean.TRUE);

		model.addAttribute("places", filter ? placeService.getPlaces(placeRequest) : Collections.emptyList());
		model.addAttribute("placeRequest", placeRequest);
		
		return "places/list";
	}

	@GetMapping("/editor/{id}")
	public String editorPlace(Model model, @PathVariable long id) {

		model.addAttribute("cities", datapoolService.getCitiesByCountry(TURKEY_COUNTRY_ID));
		model.addAttribute("title", "Place Editor");
		model.addAttribute("id", id);
		Place place = placeService.getPlaceById(id);
		if (place != null && place.getAddress() != null) {
			if (place.getAddress().getCityId() > 0) {
				model.addAttribute("districts", datapoolService.getDistrictsByCity(place.getAddress().getCityId()));
			}

			if (place.getAddress().getDistrictId() > 0) {
				model.addAttribute("regions", datapoolService.getRegionsByDistrict(place.getAddress().getDistrictId()));
			}
		}

		model.addAttribute("place", place);
		model.addAttribute("periodTypes", PERIOD_TYPES);

		return "places/editor";
	}

	@GetMapping("/editor")
	public String editorNewPlace(Model model) {
		model.addAttribute("cities", datapoolService.getCitiesByCountry(TURKEY_COUNTRY_ID));
		model.addAttribute("title", "Place Editor");
		Place place = new Place();
		place.setAddress(new Address());
		model.addAttribute("place", place);
		model.addAttribute("periodTypes", PERIOD_TYPES);

		return "places/editor";
	}

	@PostMapping(value = "/savep")
	public RedirectView savePlace(@ModelAttribute("place") Place place) {
		Place result = placeService.savePlace(place);
		RedirectView redirect = new RedirectView();
		redirect.setExposeModelAttributes(false);
		if (result != null) {
			redirect.setUrl("/places/editor/" + result.getId());
		} else {
			redirect.setUrl("/places/editor");
		}
		return redirect;
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
	public RedirectView savePage(Model model, @ModelAttribute("page") PlaceLandingPage page, BindingResult result) {
		placeService.saveLandingPage(page);

		RedirectView redirect = new RedirectView(
				"/places/" + page.getPlace().getId() + "/pages/" + page.getLanguage().getCode().toLowerCase());
		redirect.setExposeModelAttributes(false);
		return redirect;
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
