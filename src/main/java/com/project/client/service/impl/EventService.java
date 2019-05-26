package com.project.client.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.api.data.enums.EventType;
import com.project.api.data.enums.MainType;
import com.project.api.data.model.event.Event;
import com.project.api.data.model.event.EventLandingPage;
import com.project.client.service.IEventService;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EventService extends BaseApiService implements IEventService {
	@Value("${com.project.api-uri}")
	private String API_URL;
	
	@Override
	public List<Event> getEvents() {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/events");
		return (List<Event>) getList(endpoint.toString());
	}

	@Override
	public Event saveEvent(Event event) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/events");
		return (Event) postObject(endpoint.toString(), event, Event.class);
	}

	@Override
	public Event getEventById(long id) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/events/{id}");
		return (Event) getObject(endpoint.toString(), Event.class, id);
	}

	@Override
	public EventLandingPage saveLandingPage(EventLandingPage page) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/events/pages");
		return (EventLandingPage) postObject(endpoint.toString(), page, EventLandingPage.class);
	}

	@Override
	public EventLandingPage getLandingPageByEventIdAndLanguage(long id, String language) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/events/{id}/pages?language=");
		endpoint.append(language);
		return (EventLandingPage) getObject(endpoint.toString(), EventLandingPage.class, id, language);
	}

	@Override
	public Event getEventByIdAndLanguage(long id, String language) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/events/{id}?language=");
		endpoint.append(language);
		return (Event) getObject(endpoint.toString(), Event.class, id);
	}

	@Override
	public List<Event> getEventsByType(EventType type) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/events?type=");
		endpoint.append(type);
		return (List<Event>) getList(endpoint.toString());
	}

	@Override
	public List<Event> getEventsByMainType(MainType type) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/events?mainType=");
		endpoint.append(type);
		return (List<Event>) getList(endpoint.toString());
	}

}
