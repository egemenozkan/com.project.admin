package com.project.client.service;

import java.util.List;

import com.project.api.data.enums.EventType;
import com.project.api.data.enums.MainType;
import com.project.api.data.model.event.Event;
import com.project.api.data.model.event.EventLandingPage;

public interface IEventService {
	List<Event> getEvents();
	List<Event> getEventsByType(EventType type);
	List<Event> getEventsByMainType(MainType type);
	Event saveEvent(Event event);
	Event getEventById(long id);
	Event getEventByIdAndLanguage(long id, String language);
	EventLandingPage saveLandingPage(EventLandingPage page);
	EventLandingPage getLandingPageByEventIdAndLanguage(long id, String language);
}
