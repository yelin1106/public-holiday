package com.example.demo.search;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dto.PaginationDto;
import com.example.demo.dto.PublicHolidaysReqDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class SearchTest {
	
	@Autowired
	private SearchService searchService;
	
	@Test
	void getHolidays() {
		PublicHolidaysReqDto reqData = new PublicHolidaysReqDto();
		reqData.setCountryCode("US");
		reqData.setYear("2025");
		
		PaginationDto reqPage = new PaginationDto();
		reqPage.setCurrentPage(2);
		reqPage.setOffset(10);
		reqPage.setLimit(10);
		
		String result = searchService.getHolidays(reqData, reqPage);
		Assertions.assertThat(result).isEqualTo("Success");
	}
	
	
}
