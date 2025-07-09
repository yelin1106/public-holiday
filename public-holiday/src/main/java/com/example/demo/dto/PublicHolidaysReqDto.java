package com.example.demo.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class PublicHolidaysReqDto {
	
	private String countryCode;
	private String year;
	
	/* 기간 */
	private String from;
	private String to;
	
	/* 공휴일 타입 */
	private String type;
	
	//추가 필터
//	private String name;
//	private String fixed;
//	private String global;
	
	//정렬
//	private String orderColumn;
//	private String sortOrder;
	
	//paging
	private int currentPage;
	private int limit;
	
}
