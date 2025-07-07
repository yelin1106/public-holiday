package com.example.demo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_COUNTRY_CODE")
public class TbCountryCode {
	
	@Id
	@Column(name = "country_code", length = 2)
	private String countryCode;
	
	@Column(name = "name", nullable = false, length = 50)
	private String name;
	
}
