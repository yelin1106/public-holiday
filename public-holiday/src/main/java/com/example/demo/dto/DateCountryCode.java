package com.example.demo.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DateCountryCode implements Serializable {
	
//	@Column(name = "date")
	private String date;
	
//	@Column(name = "country_code")
	private String countryCode;
	
}
