package com.example.demo.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class PaginationDto {
	
	private long offset;
	private long limit;
	private long totalItems;
	private long totalPages;
	private long currentPage;
	
}
