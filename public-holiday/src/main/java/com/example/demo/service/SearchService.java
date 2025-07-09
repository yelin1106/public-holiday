package com.example.demo.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PaginationDto;
import com.example.demo.dto.PublicHolidaysReqDto;
import com.example.demo.dto.PublicHolidaysResDto;
import com.example.demo.repository.SearchRepository;

import jakarta.transaction.Transactional;

@Service
public class SearchService {
	
	@Autowired
	private SearchRepository searchRepository;
	
	@Transactional
	public String getHolidays(PublicHolidaysReqDto reqData, PaginationDto reqPage) {
		
		long totalCnt = searchRepository.getCountryHolidaysCnt(reqData);
		List<PublicHolidaysResDto> resultList = searchRepository.getCountryHolidays(reqData, reqPage);
		
		/* pagination */
		PaginationDto pageDto = new PaginationDto();
		pageDto.setOffset(reqPage.getOffset());
		pageDto.setLimit(reqPage.getLimit());
		pageDto.setCurrentPage(reqPage.getCurrentPage());
		pageDto.setTotalItems(totalCnt);
		long pages = (totalCnt%pageDto.getLimit() == 0) ? (totalCnt/pageDto.getLimit()) : (totalCnt/pageDto.getLimit() + 1);
		pageDto.setTotalPages(pages);
		
		JSONObject response = new JSONObject();
		response.put("data", new JSONArray(resultList));
		response.put("pagination", new JSONObject(pageDto));
		
		return response.toString();
	}
	
}
