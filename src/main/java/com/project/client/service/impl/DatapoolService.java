package com.project.client.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.api.data.model.common.DestinationAutocomplete;
import com.project.api.data.model.common.IdValue;
import com.project.client.service.IDatapoolService;
import com.project.common.model.City;
import com.project.common.model.Country;
import com.project.common.model.Nationality;
import com.project.common.model.Region;
import com.project.common.model.Subregion;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<City> getCitiesByCountryId(int countryId) {
		StringBuffer endpoint = new StringBuffer(API_URL);
		endpoint.append("/api/v1/datapool/countries/{countryId}/cities");
		return (List<City>) getList(endpoint.toString(), countryId);
	}

	@Override
	public List<Region> getRegionsByCityId(int cityId) {
		StringBuffer endpoint = new StringBuffer(API_URL);
		endpoint.append("/api/v1/datapool/cities/{cityId}/regions");
		return (List<Region>) getList(endpoint.toString(), cityId);
	}

	@Override
	public List<Subregion> getSubregionsByRegionId(int regionId) {
		StringBuffer endpoint = new StringBuffer(API_URL);
		endpoint.append("/api/v1/datapool/regions/{regionId}/subregions");
		return (List<Subregion>) getList(endpoint.toString(), regionId);
	}

	@Override
	public List<DestinationAutocomplete> getDestinations() {
		// TODO Auto-generated method stub
		return null;
	}

}
