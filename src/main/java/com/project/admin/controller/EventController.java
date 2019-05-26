package com.project.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.api.data.enums.EventPeriodType;
import com.project.api.data.enums.EventType;
import com.project.api.data.enums.Language;
import com.project.api.data.model.common.Content;
import com.project.api.data.model.event.Event;
import com.project.api.data.model.event.EventLandingPage;
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
	private static final EventPeriodType[] PERIOD_TYPES = EventPeriodType.values();

	@GetMapping({ "/list", "/list/{mainType}", "/list/{mainType}/{type}" })
	public String listEvents(Model model, @PathVariable(required = false) String mainType, @PathVariable(required = false) String type) {
		model.addAttribute("title", "Events");
		List<Event> events = null;
		if (mainType == null && type == null) {
			events = eventService.getEvents();
		}

		if (mainType != null && type != null) {
			type = type.toUpperCase();
			events = eventService.getEventsByType(EventType.valueOf(type));
		}
		model.addAttribute("events", events);

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
		return "events/editor";
	}

	@GetMapping("/editor")
	public String editorNewEvent(Model model) {

		model.addAttribute("title", "Event Editor");
		Event event = new Event();
		model.addAttribute("event", event);
		model.addAttribute("eventTypes", EVENT_TYPES);
		model.addAttribute("periodTypes", PERIOD_TYPES);
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
	public String editorLandingPage(Model model, @PathVariable long id, @PathVariable String language) {

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
	public String savePage(Model model, @ModelAttribute("page") @Valid EventLandingPage page, BindingResult bindingResult) throws Exception{        
	    if(bindingResult.hasErrors()){            
	         LOG.debug("::saveEventPage {}",bindingResult.getFieldError().getDefaultMessage());     
	     }                
		eventService.saveLandingPage(page);

		return "redirect:/events/" + page.getEvent().getId() + "/pages/" + page.getLanguage().getCode().toLowerCase();

	}

}
