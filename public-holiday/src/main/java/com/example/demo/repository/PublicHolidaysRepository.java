package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.DateCountryCodeDto;
import com.example.demo.dto.TbPublicHolidaysDto;

@Repository
public interface PublicHolidaysRepository extends JpaRepository<TbPublicHolidaysDto, DateCountryCodeDto> {
	
}
