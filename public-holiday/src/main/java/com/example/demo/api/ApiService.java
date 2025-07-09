package com.example.demo.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.demo.dto.CountryYearDto;
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
		
		/* 국가 리스트 가져오기 */
		List<TbCountryCodeDto> countryList = countryCodeRepository.findAll();
		
		/* 전세계 5년치 데이터 받아오기 */
		List<TbPublicHolidaysDto> resList = new ArrayList<TbPublicHolidaysDto>();
		
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
						log.info(tmp.toString());
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
	
	public String updatePublicHolidays(CountryYearDto dto) {
		
		/* 데이터 조회 */
		WebClient webClient = webClientBuilder.build();
		String uri = "https://date.nager.at/api/v3/PublicHolidays/";
		
		try {
			List<TbPublicHolidaysDto> list = webClient.get()
					.uri(uri + dto.getYear() + "/" + dto.getCountryCode())
					.header("Content-Type", "application/json")
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToFlux(TbPublicHolidaysDto.class)
					.collectList()
					.block();
			
			publicHolidaysRepository.saveAll(list);
			
		} catch (WebClientResponseException e) {
			return e.getMessage();
		}
		
		return "Success";
	}
	
	public int deletePublicHolidays(CountryYearDto dto) {
		
		int cnt = publicHolidaysRepository.deleteByDateAndCountryCode(dto.getYear()+"%", dto.getCountryCode());
		
		return cnt;
	}
	
}
