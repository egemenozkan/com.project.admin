package com.project.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.client.service.IDatapoolService;
import com.project.common.model.City;
import com.project.common.model.Country;
import com.project.common.model.Nationality;
import com.project.common.model.Region;
import com.project.common.model.Subregion;

@Controller
public class DatapoolController {

	private final static List<String> COUNTRIES = new ArrayList<String>() {
		{
			add("Turkey");
		}
	};
	private final static int COUNTRY_ID = 223;

	@Autowired
	private IDatapoolService datapoolService;

	@RequestMapping(value = "/nationalities", method = RequestMethod.GET)
	public @ResponseBody List<Nationality> getNationalities() {

		List<Nationality> nationalities = datapoolService.getNationalities();

		return nationalities;
	}


	@RequestMapping(value = "/countries", method = RequestMethod.GET)
	public @ResponseBody List<Country> getCountries() {
		List<Country> countries = datapoolService.getCountries();

		return countries;
	}

	@RequestMapping(value = "/countries/{countryId}/cities", method = RequestMethod.GET)
	public @ResponseBody List<City> getCitiesByCountryId(@PathVariable int countryId) {
		List<City> cities = datapoolService.getCitiesByCountryId(countryId);
		return cities;
	}

	@RequestMapping(value = "/cities/{cityId}/regions", method = RequestMethod.GET)
	public @ResponseBody List<Region> getRegionsByCityId(@PathVariable int cityId) {
		List<Region> regions = datapoolService.getRegionsByCityId(cityId);
		return regions;
	}

	@RequestMapping(value = "/regions/{regionId}/subregions", method = RequestMethod.GET)
	public @ResponseBody List<Subregion> getSubregions(@PathVariable int regionId) {
		List<Subregion> subregions = datapoolService.getSubregionsByRegionId(regionId);

		return subregions;
	}

}
