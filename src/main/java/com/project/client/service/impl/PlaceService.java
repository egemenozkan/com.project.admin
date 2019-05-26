package com.project.client.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.api.data.enums.MainType;
import com.project.api.data.enums.PlaceType;
import com.project.api.data.model.place.Place;
import com.project.api.data.model.place.PlaceLandingPage;
import com.project.client.service.IPlaceService;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PlaceService extends BaseApiService implements IPlaceService {
	@Value("${com.project.api-uri}")
	private String API_URL;
	
	@Override
	public List<Place> getPlaces() {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/places");
		return (List<Place>) getList(endpoint.toString());
	}

	@Override
	public Place savePlace(Place place) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/places");
		return (Place) postObject(endpoint.toString(), place, Place.class);
	}

	@Override
	public Place getPlaceById(long id) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/places/{id}");
		return (Place) getObject(endpoint.toString(), Place.class, id);
	}

	@Override
	public PlaceLandingPage saveLandingPage(PlaceLandingPage page) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/places/pages");
		return (PlaceLandingPage) postObject(endpoint.toString(), page, PlaceLandingPage.class);
	}

	@Override
	public PlaceLandingPage getLandingPageByPlaceIdAndLanguage(long id, String language) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/places/{id}/pages");
		endpoint.append("?language=").append(language);
		return (PlaceLandingPage) getObject(endpoint.toString(), PlaceLandingPage.class, id);
	}

	@Override
	public Place getPlaceByIdAndLanguage(long id, String language) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/places/{id}");
		endpoint.append("?language=").append(language);
		return (Place) getObject(endpoint.toString(), Place.class, id);
	}

	@Override
	public List<Place> getPlacesByType(PlaceType type) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/places?type=");
		endpoint.append(type);
		return (List<Place>) getList(endpoint.toString());
	}

	@Override
	public List<Place> getPlacesByMainType(MainType type) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/places?mainType=");
		endpoint.append(type);
		return (List<Place>) getList(endpoint.toString());
	}

	@Override
	public List<Place> autocompletePlaces(String query) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/places/autocomplete?query=");
		endpoint.append(query);
		return (List<Place>) getList(endpoint.toString());
	}

	@Override
	public void setMainImage(long id, long fileId) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/places/{id}/main-image");
		postObject(endpoint.toString(), Long.valueOf(fileId), Boolean.class, id);		
	}
	

}
