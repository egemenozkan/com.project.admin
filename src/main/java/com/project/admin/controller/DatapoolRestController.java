package com.project.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.api.data.model.gis.City;
import com.project.api.data.model.gis.Country;
import com.project.api.data.model.gis.District;
import com.project.api.data.model.gis.Region;
import com.project.client.service.IDatapoolService;
import com.project.common.model.Nationality;

@RestController
public class DatapoolRestController {

	@Autowired
	private IDatapoolService datapoolService;

	@GetMapping(value = "/nationalities")
	public @ResponseBody List<Nationality> getNationalities() {
		return  datapoolService.getNationalities();
	}

	@GetMapping(value = "/countries")
	public @ResponseBody List<Country> getCountries() {
		return datapoolService.getCountries();
	}

	@GetMapping(value = "/countries/{countryId}/cities")
	public @ResponseBody List<City> getCitiesByCountryId(@PathVariable int countryId) {
		return datapoolService.getCitiesByCountry(countryId);
	}

	@GetMapping(value = "/cities/{cityId}/regions")
	public @ResponseBody List<Region> getRegionsByCityId(@PathVariable int cityId) {
		return datapoolService.getRegionsByCity(cityId);
	}
	
	@GetMapping(value = "/cities/{cityId}/districts")
	public @ResponseBody List<District> getDistrictsByCityId(@PathVariable int cityId) {
		return datapoolService.getDistrictsByCity(cityId);
	}
	
	@GetMapping(value = "/districts/{districtId}/regions")
	public @ResponseBody List<Region> get(@PathVariable int districtId) {
		return datapoolService.getRegionsByDistrict(districtId);
	}

//	@RequestMapping(value = "/regions/{regionId}/subregions", method = RequestMethod.GET)
//	public @ResponseBody List<Subregion> getSubregions(@PathVariable int regionId) {
//		return datapoolService.getSubregionsByRegionId(regionId);
//	}

}
