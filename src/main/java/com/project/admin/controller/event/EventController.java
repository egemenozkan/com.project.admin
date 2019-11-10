package com.project.admin.controller.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

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

import com.project.api.data.enums.FeeType;
import com.project.api.data.enums.Language;
import com.project.api.data.enums.PeriodType;
import com.project.api.data.model.common.Content;
import com.project.api.data.model.event.Event;
import com.project.api.data.model.event.EventLandingPage;
import com.project.api.data.model.event.EventRequest;
import com.project.api.data.model.event.EventStatus;
import com.project.api.data.model.event.EventType;
import com.project.api.data.model.event.TimeTable;
import com.project.client.service.IDatapoolService;
import com.project.client.service.IEventService;
import com.project.client.service.IPlaceService;

@Controller
@RequestMapping(value = "/events/")
public class EventController {

	static final Logger LOG = LoggerFactory.getLogger(EventController.class);

	@Autowired
	private IPlaceService placeService;

	@Autowired
	private IEventService eventService;

	@Autowired
	private IDatapoolService datapoolService;


	private static final EventType[] EVENT_TYPES = EventType.values();
	private static final PeriodType[] PERIOD_TYPES = PeriodType.values();
	private static final FeeType[] FEE_TYPES = FeeType.values();
	private static final EventStatus[] EVENT_STATUSES = EventStatus.values();

	@GetMapping("/list")
	public String listEvents(Model model, @RequestParam(defaultValue = "false") boolean filter, @RequestParam(defaultValue = "0") int type,
			@RequestParam(defaultValue = "") String[] types,
			@RequestParam(defaultValue = "1") int status) {
		model.addAttribute("eventTypes", EVENT_TYPES);
		model.addAttribute("eventStatuses", EVENT_STATUSES);
		model.addAttribute("title", "Events");

		if (filter) {	
			EventRequest eventRequest = new EventRequest();
			
			if (type > 0) {
				eventRequest.setType(EventType.getById(type));
			}
			if (types != null) {
				eventRequest.setTypes(types);
			}
			eventRequest.setStatus(EventStatus.getById(status));
			
			/* Show OneByOne */
			eventRequest.setDistinct(Boolean.TRUE);
			/* Data Optimisation */
			eventRequest.setHidePlace(Boolean.TRUE);
			model.addAttribute("eventRequest", eventRequest);
			model.addAttribute("events", eventService.getEvents(eventRequest));
		}
		return "events/list";
	}

	@GetMapping("/editor/{id}")
	public String editorEvent(Model model, @PathVariable long id) {

		model.addAttribute("title", "Event Editor");
		model.addAttribute("id", id);
		Event event = eventService.getEventById(id);
		model.addAttribute("event", event);
		model.addAttribute("eventTypes", EVENT_TYPES);
		model.addAttribute("periodTypes", PERIOD_TYPES);
		model.addAttribute("feeTypes", FEE_TYPES);

		return "events/editor";
	}

	@GetMapping("/editor")
	public String editorNewEvent(Model model) {

		model.addAttribute("title", "Event Editor");
		Event event = new Event();
		model.addAttribute("event", event);
		model.addAttribute("eventTypes", EVENT_TYPES);
		model.addAttribute("periodTypes", PERIOD_TYPES);
		model.addAttribute("feeTypes", FEE_TYPES);

		return "events/editor";
	}

	@PostMapping(value = "/savep")
	public String saveEvent(@ModelAttribute("event") Event event) {
		Event result = eventService.saveEvent(event);
		if (result != null) {
			return "redirect:/events/editor/" + result.getId();
		}
		return "redirect:/events/editor";
	}

	/** Landingpage **/

	@GetMapping("/{id}/pages/{language}")
	public String editorLandingPage(Model model, @PathVariable long id,@PathVariable String language) {

		if (language != null) {
			language = language.toUpperCase();
		}

		EventLandingPage page = eventService.getLandingPageByEventIdAndLanguage(id, language);
		/** Create New Page **/
		if (page == null) {
			page = new EventLandingPage();
			page.setLanguage(Language.getByCode(language));
			Event event = eventService.getEventByIdAndLanguage(id, language);
			//
			if (event == null) {
				LOG.warn("::editorLandingPage event is null for {}", id);
			}
			if (event != null) {
				page.setTitle(event.getName());
				page.setEvent(event);
				Content content = new Content();
				List<Content> contents = new ArrayList<>();
				contents.add(content);
				page.setContents(contents);
			}
		}
		model.addAttribute("page", page);

		return "events/page_editor";
	}


	@PostMapping("/pages")
	public String savePage(Model model, @ModelAttribute("page") @Valid EventLandingPage page,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			LOG.debug("::saveEventPage {}", bindingResult.getFieldError().getDefaultMessage());
		}
		eventService.saveLandingPage(page);

		return "redirect:/events/" + page.getEvent().getId() + "/pages/" + page.getLanguage().getCode().toLowerCase();

	}
	
	@GetMapping("/autocomplete")
	public @ResponseBody List<Event> autocompleteEvents(Model model, @RequestParam String query) {
		if (query != null && query.length() < 3) {
			return Collections.emptyList();
		}
		EventRequest eventRequest = new EventRequest();
		eventRequest.setName(query);
		eventRequest.setLanguage(Language.TURKISH);
		eventRequest.setHidePlace(Boolean.TRUE);
		return eventService.getEvents(eventRequest);

	}
	
	@GetMapping("/{id}/time-table")
	public @ResponseBody List<TimeTable> getTimeTable(@PathVariable long id) {
		return eventService.getTimeTableByEventId(id);
	}
	
	@PostMapping("/time-table")
	public @ResponseBody TimeTable saveTimeTable(RequestEntity<TimeTable> requestEntity) {
		eventService.saveTimeTable(requestEntity.getBody());
		return requestEntity.getBody();
	}
	
	@DeleteMapping("/time-table/{id}")
	public @ResponseBody boolean deleteTimeTable(@PathVariable long id) {
		eventService.deleteTimeTableById(id);
		return true;
	}
	

	
	
	


}
