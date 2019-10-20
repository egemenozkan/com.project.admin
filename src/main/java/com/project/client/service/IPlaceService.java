package com.project.client.service;

import java.util.List;

import com.project.api.data.enums.MainType;
import com.project.api.data.enums.PlaceType;
import com.project.api.data.model.event.TimeTable;
import com.project.api.data.model.place.Place;
import com.project.api.data.model.place.PlaceLandingPage;
import com.project.api.data.model.place.PlaceRequest;

public interface IPlaceService {
	List<Place> getPlaces();
	List<Place> getPlacesByType(PlaceType type);
	List<Place> getPlacesByMainType(MainType type);
	Place savePlace(Place place);
	Place getPlaceById(long id);
	Place getPlaceByIdAndLanguage(long id, String language);
	PlaceLandingPage saveLandingPage(PlaceLandingPage page);
	PlaceLandingPage getLandingPageByPlaceIdAndLanguage(long id, String language);
	List<Place> autocompletePlaces(String query);
	void setMainImage(long id, long fileId);
	List<TimeTable> getTimeTableByPlaceId(long placeId);
	int saveTimeTable(TimeTable timeTable);
	int deleteTimeTableById(long id);
	List<Place> getPlaces(PlaceRequest placeRequest);


}
