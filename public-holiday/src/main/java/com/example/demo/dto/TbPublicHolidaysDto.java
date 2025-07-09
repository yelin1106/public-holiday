package com.example.demo.dto;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@IdClass(DateCountryCodeDto.class)
@Table(name = "TB_PUBLIC_HOLIDAYS")
public class TbPublicHolidaysDto {
	
	@Id
	@Column(length = 10)
	private String date;
	
	@Id
	@Column(length = 2)
	private String countryCode;
	
	@Column(nullable = false)
	private String localName;
	
	@Column(nullable = false)
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
