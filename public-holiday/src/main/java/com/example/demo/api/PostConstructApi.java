package com.example.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.service.ApiService;

import jakarta.annotation.PostConstruct;

@Component
public class PostConstructApi {
	
	@Autowired
	private ApiService apiService;
	
	@PostConstruct
	public void init() {
		apiService.getCountryCode();
		apiService.getPublicHolidays(5);
	}
	
}
