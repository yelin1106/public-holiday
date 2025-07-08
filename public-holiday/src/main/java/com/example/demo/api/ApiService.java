package com.example.demo.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.dto.TbCountryCodeDto;
import com.example.demo.dto.TbPublicHolidaysDto;
import com.example.demo.repository.CountryCodeRepository;
import com.example.demo.repository.PublicHolidaysRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiService {
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private CountryCodeRepository countryCodeRepository;
	
	@Autowired
	private PublicHolidaysRepository publicHolidaysRepository;

	public String getCountryCode() {
		
		WebClient webClient = webClientBuilder.build();
		String uri = "https://date.nager.at/api/v3/AvailableCountries";
		
		List<TbCountryCodeDto> countryList = webClient.get()
				.uri(uri)
				.retrieve()
				.bodyToFlux(TbCountryCodeDto.class)
				.collectList()
				.block();
		
		countryCodeRepository.saveAll(countryList);
		
		return "Success";
	}
	
	public String getPublicHolidays() {
		WebClient webClient = webClientBuilder.build();
		String uri = "https://date.nager.at/api/v3/PublicHolidays/";
		
		//국가 리스트 가져오기
		List<TbCountryCodeDto> countryList = countryCodeRepository.findAll();
		
		//전세계 5년치 데이터 받아오기
		List<TbPublicHolidaysDto> resList = new ArrayList<TbPublicHolidaysDto>();
		
		//최근 5년 연도 가져오기
		List<String> years = getRecentFiveYears();
		for(String year : years) {
			for(TbCountryCodeDto countryCode : countryList) {
				
				try {
					List<TbPublicHolidaysDto> tmpList = webClient.get()
							.uri(uri + year + "/" + countryCode.getCountryCode())
							.header("Content-Type", "application/json")
							.accept(MediaType.APPLICATION_JSON)
							.retrieve()
							.bodyToFlux(TbPublicHolidaysDto.class)
							.collectList()
							.block();
					resList.addAll(tmpList);
				} catch (Exception e) {
					
					log.info("===================WebClient Exception================");
					log.info("{}/{}",year,countryCode.getCountryCode());
					
					String strList = webClient.get()
							.uri(uri + year + "/" + countryCode.getCountryCode())
							.header("Content-Type", "application/json")
							.accept(MediaType.APPLICATION_JSON)
							.retrieve()
							.bodyToMono(String.class)
							.block();
					
					Gson gson = new Gson();
					List<TbPublicHolidaysDto> tmpList = gson.fromJson(strList, new TypeToken<ArrayList<TbPublicHolidaysDto>>(){}.getType());
					
					for(TbPublicHolidaysDto tmp : tmpList) {
						System.out.println(tmp.toString());
					}
					
					resList.addAll(tmpList);
				}
				
			}
		}
		
		publicHolidaysRepository.saveAll(resList);
		
		return "Success";
	}
	
	public List<String> getRecentFiveYears() {
		List<String> years = new ArrayList<>();
		
		int currentYear = LocalDate.now().getYear();
		for(int i=0; i<5; i++) {
			years.add(Integer.toString(currentYear-i));
		}
		
		return years;
	}
	
}
