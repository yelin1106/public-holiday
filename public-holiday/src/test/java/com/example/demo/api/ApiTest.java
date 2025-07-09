package com.example.demo.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ApiTest {
	
	@Autowired
	private ApiService apiService;
	
	@Test
	void getCountryCode() {
		String result = apiService.getCountryCode();
		Assertions.assertThat(result).isEqualTo("Success");
	}
	
	@Test
	void getPublicHolidays() {
		apiService.getCountryCode();
		
		String result = apiService.getPublicHolidays(5);
		Assertions.assertThat(result).isEqualTo("Success");
	}
	
}
