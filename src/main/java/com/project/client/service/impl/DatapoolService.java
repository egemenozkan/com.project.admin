package com.project.client.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.project.api.data.model.common.DestinationAutocomplete;
import com.project.api.data.model.common.IdValue;
import com.project.api.data.model.gis.City;
import com.project.api.data.model.gis.Country;
import com.project.api.data.model.gis.District;
import com.project.api.data.model.gis.Region;
import com.project.api.data.model.gis.Subregion;
import com.project.client.service.IDatapoolService;
import com.project.common.model.Nationality;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DatapoolService extends BaseApiService implements IDatapoolService {
	@Value("${com.project.api-uri}")
	private String API_URL;

	@Override
	public List<Nationality> getNationalities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Country> getCountries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IdValue> getColors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<City> getCitiesByCountry(int id) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/gis/countries/{id}/cities");
		return getArrayList(endpoint.toString(), new ParameterizedTypeReference<List<City>>() {
		}, id);
	}

	@Override
	public List<Subregion> getSubregionsByRegionId(int regionId) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/datapool/regions/{regionId}/subregions");
		return (List<Subregion>) getList(endpoint.toString(), regionId);
	}

	@Override
	public List<DestinationAutocomplete> getDestinations() {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public List<Region> getRegionsByDistrict(int id) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/gis/districts/{id}/regions");
		return (List<Region>) getList(endpoint.toString(), id);
	}

	@Override
	public List<District> getDistrictsByCity(int id) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/gis/cities/{id}/districts");
		return (List<District>) getList(endpoint.toString(), id);
	}

	@Override
	public List<Region> getRegionsByCity(int id) {
		StringBuilder endpoint = new StringBuilder(API_URL);
		endpoint.append("/api/v1/gis/cities/{id}/regions");
		return (List<Region>) getList(endpoint.toString(), id);
	}

}
