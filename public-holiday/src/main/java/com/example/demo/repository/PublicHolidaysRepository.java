package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.DateCountryCodeDto;
import com.example.demo.dto.TbPublicHolidaysDto;

@Repository
public interface PublicHolidaysRepository extends JpaRepository<TbPublicHolidaysDto, DateCountryCodeDto> {
	
	@Modifying
	@Transactional
	@Query("delete "
			+ "from TbPublicHolidaysDto "
			+ "where date like :date "
			+ "and countryCode = :countryCode ")
	public int deleteByDateAndCountryCode(@Param("date") String date, @Param("countryCode") String countryCode);
	
}
