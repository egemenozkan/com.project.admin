package com.project.client.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.api.data.enums.EventType;
import com.project.api.data.enums.MainType;
import com.project.api.data.model.event.Event;
import com.project.api.data.model.event.EventLandingPage;
import com.project.api.data.model.event.EventRequest;
import com.project.api.data.model.event.TimeTable;
import com.project.client.service.IEventService;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EventService extends BaseApiService implements IEventService {
	@Value("${com.project.api-uri}")
	private String API_URL;

	@Override
	public List<Event> getEvents(EventRequest eventRequest) {
		UriComponentsBuilder endpoint = UriComponentsBuilder.fromUriString(API_URL + "/api/v1/events");

		if (eventRequest.getLanguage() != null) {
			endpoint.queryParam("language", eventRequest.getLanguage().getCode());
		}
		if (eventRequest.getLimit() > 0) {
			endpoint.queryParam("limit", eventRequest.getLimit());
		}
		if (eventRequest.getType() != null) {
			endpoint.queryParam("type", eventRequest.getType().getId());
		}
		if (eventRequest.getTypes() != null) {
			endpoint.queryParam("types", String.join(",", eventRequest.getTypes()));
		}
		if (eventRequest.getRandom() != null && eventRequest.getRandom()) {
			endpoint.queryParam("random", eventRequest.getRandom());
		}
		if (eventRequest.getStartDate() != null) {
			endpoint.queryParam("startDate", eventRequest.getStartDate());
		}
		if (eventRequest.getEndDate() != null) {
			endpoint.queryParam("endDate", eventRequest.getEndDate());
		}
		if (eventRequest.getPlaceId() > 0) {
			endpoint.queryParam("placeId", eventRequest.getPlaceId());
		}
		if (eventRequest.getPeriodType() != null) {
			endpoint.queryParam("periodType", eventRequest.getPeriodType().getId());
		}
		if (eventRequest.getDistinct() != null) {
			endpoint.queryParam("distinct", eventRequest.getDistinct());
		}
		return getList(endpoint.toUriString(), new ParameterizedTypeReference<List<Event>>() {
		});
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

	@Override
	public List<Event> autocompleteEvents(String query) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/events/autocomplete?query=");
		endpoint.append(query);
		return (List<Event>) getList(endpoint.toString());
	}

	@Override
	public List<TimeTable> getTimeTableByEventId(long eventId) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/events/{id}/time-table");
		return (List<TimeTable>) getList(endpoint.toString(), eventId);
	}

	@Override
	public int saveTimeTable(TimeTable timeTable) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/events/time-table");
		return (int) postObject(endpoint.toString(), timeTable, Integer.class);
	}

	@Override
	public int deleteTimeTableById(long id) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/events/time-table/{id}");
		deleteObject(endpoint.toString(), id);
		return 0;
	}
//	@Override
//	public int deleteTimeTableById(long id) {
//		StringBuilder endpoint = new StringBuilder(API_URL);
//		endpoint.append("/api/v1/events/{id}/time-table");
//		return (int) getObject(endpoint.toString(), Integer.class, id);
//	}

}
