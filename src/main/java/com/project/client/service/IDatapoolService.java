package com.project.client.service;

import java.util.List;

import com.project.api.data.model.common.DestinationAutocomplete;
import com.project.api.data.model.common.IdValue;
import com.project.api.data.model.gis.City;
import com.project.api.data.model.gis.Country;
import com.project.api.data.model.gis.District;
import com.project.api.data.model.gis.Region;
import com.project.api.data.model.gis.Subregion;
import com.project.common.model.Nationality;

public interface IDatapoolService {

	List<Nationality> getNationalities();

	List<Country> getCountries();

	List<IdValue> getColors();

	List<City> getCitiesByCountry(int countryId);

	List<Subregion> getSubregionsByRegionId(int regionId);

	List<DestinationAutocomplete> getDestinations();

	List<Region> getRegionsByDistrict(int id);

	List<District> getDistrictsByCity(int id);

	List<Region> getRegionsByCity(int id);

}
