package com.example.demo.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dto.CountryYearDto;
import com.example.demo.service.ApiService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class deleteTest {
	
	@Autowired
	private ApiService apiService;
	
	@Test
	void updatePublicHolidays() {
		
		CountryYearDto dto = new CountryYearDto();
		dto.setYear("2025");
		dto.setCountryCode("KR");
		
		int result = apiService.deletePublicHolidays(dto);
		
	}
	
	
}
