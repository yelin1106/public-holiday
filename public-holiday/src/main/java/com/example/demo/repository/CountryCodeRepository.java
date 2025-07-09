package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.TbCountryCodeDto;

@Repository
public interface CountryCodeRepository extends JpaRepository<TbCountryCodeDto, String> {

}
