package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.DateCountryCode;
import com.example.demo.dto.TbPublicHolidays;

public interface PublicHolidaysRepository extends JpaRepository<TbPublicHolidays, DateCountryCode> {
	
	
	
}
