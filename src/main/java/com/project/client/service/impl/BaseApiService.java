package com.project.client.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public abstract class BaseApiService<T> {

	@Autowired
	private RestTemplate restTemplate;

	public List<?> getList(String endpoint, Object... parameters) {
		List<?> result = null;
		if (parameters == null || parameters.length == 0) {
			try {
				result = restTemplate.getForObject(endpoint, List.class);
			} catch (Exception exception) {
				exception.getLocalizedMessage();
			}
		} else {
			result = restTemplate.getForObject(endpoint, List.class, parameters);
		}

		return result;
	}

	public Object postObject(String endpoint, Object parameter, Class<T> responseType) {
		ResponseEntity<T> responseEntity = restTemplate.postForEntity(endpoint, parameter, responseType);
		T responseBody = responseEntity.getBody();
		return responseBody;
	}
//	public Object getObject(String endpoint, Object parameter, Class<T> responseType) {
//		ResponseEntity<T> responseEntity = restTemplate.getForEntity
//		T responseBody = responseEntity.getBody();
//		return responseBody;
//	}
//	
	public Object postObject(String endpoint, Object request, Class<T> responseType, Object... parameters) {
		ResponseEntity<T> responseEntity = restTemplate.postForEntity(endpoint, request, responseType, parameters);
		T responseBody = responseEntity.getBody();
		return responseBody;
	}
	public Object getObject(String endpoint, Class<T> responseType, Object... parameters) {
		ResponseEntity<T> responseEntity = null;
		if (parameters == null || parameters.length == 0) {
			try {
				responseEntity = restTemplate.getForEntity(endpoint, responseType);
			} catch (Exception exception) {
				exception.getLocalizedMessage();
			}
		} else {
			responseEntity = restTemplate.getForEntity(endpoint, responseType, parameters);
		}

		return responseEntity.getBody();
	}
	

}
