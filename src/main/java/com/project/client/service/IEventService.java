package com.project.client.service;

import java.util.List;

import com.project.api.data.enums.MainType;
import com.project.api.data.model.event.Event;
import com.project.api.data.model.event.EventLandingPage;
import com.project.api.data.model.event.EventRequest;
import com.project.api.data.model.event.EventType;
import com.project.api.data.model.event.TimeTable;

public interface IEventService {
	List<Event> getEvents(EventRequest eventRequest);
	List<Event> getEventsByType(EventType type);
	List<Event> getEventsByMainType(MainType type);
	Event saveEvent(Event event);
	Event getEventById(long id);
	Event getEventByIdAndLanguage(long id, String language);
	EventLandingPage saveLandingPage(EventLandingPage page);
	EventLandingPage getLandingPageByEventIdAndLanguage(long id, String language);
	List<Event> autocompleteEvents(String query);
	List<TimeTable> getTimeTableByEventId(long eventId);
	int saveTimeTable(TimeTable timeTable);
	int deleteTimeTableById(long id);
}
