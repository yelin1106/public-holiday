package com.example.demo.api;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.DateCountryCode;
import com.example.demo.dto.TbPublicHolidays;

public interface PublicHolidaysRepository extends JpaRepository<TbPublicHolidays, DateCountryCode> {
	
	
	
}
