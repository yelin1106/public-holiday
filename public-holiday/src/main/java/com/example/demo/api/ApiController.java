package com.example.demo.api;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CountryYearDto;

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
		
		return ResponseEntity.ok(response);
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
		
		return ResponseEntity.ok(Integer.toString(cnt));
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
