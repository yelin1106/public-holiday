package com.example.demo.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicHolidaysResDto {
	
	@Column(nullable = false)
	private String countryCode;
	
	@Column(nullable = false)
	private String countryName;
	
	@Column(nullable = false)
	private String date;
	
	@Column(nullable = true)
	private String localName;
	
	@Column(nullable = true)
	private String name;
	
	@Column(nullable = true)
	private String fixed;
	
	@Column(nullable = true)
	private String global;
	
	@Column(nullable = true)
	private List<String> counties;
	
	@Column(nullable = true)
	private String launchYear;
	
	@Column(nullable = true)
	private List<String> types;

}
