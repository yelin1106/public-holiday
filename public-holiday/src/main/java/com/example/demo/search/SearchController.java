package com.example.demo.search;
import com.example.demo.dto.PublicHolidaysReqDto;
import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseEntity<String> getPublicHolidays(
			@ModelAttribute("req") PublicHolidaysReqDto publicHolidaysReqDto) {
		
		/* req값 검증 */
		if(publicHolidaysReqDto.getYear().length()>4) {
			return ResponseEntity.badRequest().build();
		}
		if(publicHolidaysReqDto.getCountryCode().length()>2) {
			return ResponseEntity.badRequest().build();
		}
		if(publicHolidaysReqDto.getFrom().length()>10) {
			return ResponseEntity.badRequest().build();
		}
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
		
		return ResponseEntity.ok(response);
	}
	
}
