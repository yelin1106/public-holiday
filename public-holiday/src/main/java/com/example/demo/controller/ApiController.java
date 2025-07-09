package com.example.demo.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CountryYearDto;
import com.example.demo.service.ApiService;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private ApiService apiService;
	
	@PutMapping("/update/{year}/{countryCode}")
	public ResponseEntity<String> updatePublicHolidays(
			@PathVariable("year") String year,
			@PathVariable("countryCode") String countryCode) {
		
		/* req값 검증 */
		if(!isValidYear(year)) {
			return ResponseEntity.badRequest().build();
		}
		if(!isValidcountryCode(countryCode)) {
			return ResponseEntity.badRequest().build();
		}
		
		CountryYearDto dto = new CountryYearDto(year, countryCode);
		
		String response = apiService.updatePublicHolidays(dto);
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("status", 200);
		jsonObject.addProperty("message", response);
		
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(jsonObject.toString());
	}
	
	@DeleteMapping("/delete/{year}/{countryCode}")
	public ResponseEntity<String> deletePublicHolidays(
			@PathVariable("year") String year,
			@PathVariable("countryCode") String countryCode) {
		
		/* req값 검증 */
		if(!isValidYear(year)) {
			return ResponseEntity.badRequest().build();
		}
		if(!isValidcountryCode(countryCode)) {
			return ResponseEntity.badRequest().build();
		}
		
		CountryYearDto dto = new CountryYearDto(year, countryCode);
		
		int cnt = apiService.deletePublicHolidays(dto);
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("status", 200);
		jsonObject.addProperty("count", cnt);
		
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(jsonObject.toString());
	}
	
    public boolean isValidYear(String year) {
        String regex = "^[0-9]{4}$";
        return Pattern.compile(regex).matcher(year).matches();
    }
	
    public boolean isValidcountryCode(String countryCode) {
    	String regex = "^[A-Z]{2}$";
    	return Pattern.compile(regex).matcher(countryCode).matches();
    }
    
}
