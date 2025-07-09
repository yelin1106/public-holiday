package com.example.demo.controller;
import com.example.demo.dto.PublicHolidaysReqDto;
import com.example.demo.service.SearchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PaginationDto;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchService searchService;

	@GetMapping("/publicHolidays")
	@Operation(summary = "연도, 국가, 기간(form-to) 필터 기반 공휴일 조회", description = "연도, 국가, 기간(form-to) 필터 기반 전세계의 공휴일 조회")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json"))
	})
	public ResponseEntity<String> getPublicHolidays(
			@ModelAttribute("req") PublicHolidaysReqDto publicHolidaysReqDto) {
		
		System.out.println(publicHolidaysReqDto.toString());
		
		/* req값 검증 */
		if(publicHolidaysReqDto.getYear()!=null)
			if(publicHolidaysReqDto.getYear().length()>4) {
				return ResponseEntity.badRequest().build();
			}
		if(publicHolidaysReqDto.getCountryCode()!=null)
			if(publicHolidaysReqDto.getCountryCode().length()>2) {
				return ResponseEntity.badRequest().build();
			}
		if(publicHolidaysReqDto.getFrom()!=null)
			if(publicHolidaysReqDto.getFrom().length()>10) {
				return ResponseEntity.badRequest().build();
			}
		if(publicHolidaysReqDto.getTo()!=null)
			if(publicHolidaysReqDto.getTo().length()>10) {
				return ResponseEntity.badRequest().build();
			}
		
		
		/* page값 검증 */
		PaginationDto paginationDto = new PaginationDto();
		
		if(publicHolidaysReqDto.getCurrentPage()==0) {
			paginationDto.setCurrentPage(1);
		}else {
			paginationDto.setCurrentPage(publicHolidaysReqDto.getCurrentPage());
		}
		if(publicHolidaysReqDto.getLimit()==0) {
			paginationDto.setLimit(10);
		}else {
			paginationDto.setLimit(publicHolidaysReqDto.getLimit());
		}
		
		long offset = (paginationDto.getCurrentPage()-1) * paginationDto.getLimit();
		paginationDto.setOffset(offset);
		
		String response = searchService.getHolidays(publicHolidaysReqDto, paginationDto);
		
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(response);
	}
	
}
