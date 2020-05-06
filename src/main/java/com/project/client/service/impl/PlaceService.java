package com.project.client.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.api.data.enums.MainType;
import com.project.api.data.enums.PlaceType;
import com.project.api.data.model.event.Event;
import com.project.api.data.model.event.EventType;
import com.project.api.data.model.event.TimeTable;
import com.project.api.data.model.place.Place;
import com.project.api.data.model.place.PlaceLandingPage;
import com.project.api.data.model.place.PlaceRequest;
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

	@Override
	public List<TimeTable> getTimeTableByPlaceId(long placeId) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/places/{id}/time-table");
		return (List<TimeTable>) getList(endpoint.toString(), placeId);
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

	@Override
	public List<Place> getPlaces(PlaceRequest placeRequest) {
		UriComponentsBuilder endpoint = UriComponentsBuilder.fromUriString(API_URL + "/api/v1/places");

		if (placeRequest.getLanguage() != null) {
			endpoint.queryParam("language", placeRequest.getLanguage().getCode());
		}
		if (placeRequest.getLimit() > 0) {
			endpoint.queryParam("limit", placeRequest.getLimit());
		}
		if (placeRequest.getType() != null) {
			endpoint.queryParam("type", placeRequest.getType().getId());
		}
		if (placeRequest.getTypes() != null
				&& !Arrays.asList(placeRequest.getTypes()).contains(String.valueOf(EventType.ALL.getId()))) {
			endpoint.queryParam("types", String.join(",", placeRequest.getTypes()));
		}
		if (placeRequest.getRandom() != null && placeRequest.getRandom()) {
			endpoint.queryParam("random", placeRequest.getRandom());
		}
		if (placeRequest.getRandom() != null && placeRequest.getRandom()) {
			endpoint.queryParam("random", placeRequest.getRandom());
		}
		if (placeRequest.isHideAddress()) {
			endpoint.queryParam("hideAddress", placeRequest.isHideAddress());
		}
		if (placeRequest.isHideContact()) {
			endpoint.queryParam("hideContact", placeRequest.isHideContact());
		}
		if (placeRequest.isHideContent()) {
			endpoint.queryParam("hideContent", placeRequest.isHideContent());
		}
		if (placeRequest.isHideImages()) {
			endpoint.queryParam("hideImages", placeRequest.isHideImages());
		}
		if (placeRequest.isHideMainImage()) {
			endpoint.queryParam("hideMainImage", placeRequest.isHideMainImage());
		}
		if (placeRequest.getCityId() > 0) {
			endpoint.queryParam("city", placeRequest.getCityId());
		}
//		if (placeRequest.getDistrictId() > 0) {
//			endpoint.queryParam("district", placeRequest.getDistrictId());
//		}
		if (placeRequest.getLimit() > 0) {
			endpoint.queryParam("limit", placeRequest.getLimit());
		}
		return getList(endpoint.toUriString(), new ParameterizedTypeReference<List<Place>>() {
		});
	}

}
