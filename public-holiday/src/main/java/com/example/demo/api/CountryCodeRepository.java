package com.example.demo.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.TbCountryCode;

@Repository
public interface CountryCodeRepository extends JpaRepository<TbCountryCode, String> {

}
