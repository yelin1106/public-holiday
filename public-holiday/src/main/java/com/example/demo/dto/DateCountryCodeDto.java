package com.example.demo.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DateCountryCodeDto implements Serializable {
	
//	@Column(name = "date")
	private String date;
	
//	@Column(name = "country_code")
	private String countryCode;
	
}
