package com.example.demo.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PaginationDto;
import com.example.demo.dto.PublicHolidaysReqDto;
import com.example.demo.dto.PublicHolidaysResDto;
import com.example.demo.repository.SearchRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class SearchService {
	
	@Autowired
	private SearchRepository searchRepository;
	
	@Transactional
	public String getHolidays(PublicHolidaysReqDto reqData, PaginationDto reqPage) {
		
		long totalCnt = searchRepository.getCountryHolidaysCnt(reqData);
		List<PublicHolidaysResDto> resultList = searchRepository.getCountryHolidays(reqData, reqPage);
		
		//paging 형태로 응답
		ObjectMapper mapper = new ObjectMapper();
		
		//pagination
		PaginationDto pageDto = new PaginationDto();
		pageDto.setOffset(reqPage.getOffset());
		pageDto.setLimit(reqPage.getLimit());
		pageDto.setCurrentPage(reqPage.getCurrentPage());
		pageDto.setTotalItems(totalCnt);
		long pages = (totalCnt%pageDto.getLimit() == 0) ? (totalCnt/pageDto.getLimit()) : (totalCnt/pageDto.getLimit() + 1);
		pageDto.setTotalPages(pages);
		
		try {
			String data = mapper.writeValueAsString(resultList);
			String pagination = mapper.writeValueAsString(pageDto);
			System.out.println(data);
			System.out.println(pagination);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		//links?
		
		return "Success";
	}
	
}
