package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.PaginationDto;
import com.example.demo.dto.PublicHolidaysReqDto;
import com.example.demo.dto.PublicHolidaysResDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class SearchRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public long getCountryHolidaysCnt(PublicHolidaysReqDto reqData) {
		
		StringBuilder query = new StringBuilder();
		query.append("select count(*) ");
		query.append("from TbPublicHolidaysDto ph ");
		query.append("left join TbCountryCodeDto cc ");
		query.append("on ph.countryCode = cc.countryCode ");
		query.append("where 1=1 ");
		
		if(reqData.getCountryCode()!="" && reqData.getCountryCode()!=null)
			query.append("and ph.countryCode = :countryCode ");
		if(reqData.getYear()!="" && reqData.getYear()!=null)
			query.append("and ph.date like :year ");
		if(reqData.getFrom()!="" && reqData.getFrom()!=null
				&& reqData.getTo()!="" && reqData.getTo()!=null)
			query.append("and ph.date between :from AND :to ");
		
		TypedQuery<Long> queryEntity = entityManager.createQuery(query.toString(), Long.class);
		
		if(reqData.getCountryCode()!="" && reqData.getCountryCode()!=null)
			queryEntity.setParameter("countryCode", reqData.getCountryCode());
		if(reqData.getYear()!="" && reqData.getYear()!=null)
			queryEntity.setParameter("year", reqData.getYear()+"%");
		if(reqData.getFrom()!="" && reqData.getFrom()!=null
				&& reqData.getTo()!="" && reqData.getTo()!=null) {
			queryEntity.setParameter("from", reqData.getFrom());
			queryEntity.setParameter("to", reqData.getTo());
		}
		
		long cnt = queryEntity.getSingleResult();
		
		return cnt;
	}
	
	public List<PublicHolidaysResDto> getCountryHolidays(PublicHolidaysReqDto reqData, PaginationDto reqPage) {
		
		StringBuilder query = new StringBuilder();
		query.append("select ph.countryCode, cc.name countryName, ph.date, ph.localName, ph.name, ph.fixed, ph.global, ph.counties, ph.launchYear, ph.types ");
		query.append("from TbPublicHolidaysDto ph ");
		query.append("left join TbCountryCodeDto cc ");
		query.append("on ph.countryCode = cc.countryCode ");
		query.append("where 1=1 ");
		
		if(reqData.getCountryCode()!="" && reqData.getCountryCode()!=null)
			query.append("and ph.countryCode = :countryCode ");
		if(reqData.getYear()!="" && reqData.getYear()!=null)
			query.append("and ph.date like :year ");
		if(reqData.getFrom()!="" && reqData.getFrom()!=null
				&& reqData.getTo()!="" && reqData.getTo()!=null)
			query.append("and ph.date between :from AND :to ");
		
		query.append("order by ph.date ");
		query.append("limit :limit ");
		query.append("offset :offset ");
		
		TypedQuery<PublicHolidaysResDto> queryEntity = entityManager
				.createQuery(query.toString(), PublicHolidaysResDto.class)
				.setParameter("limit", reqPage.getLimit())
				.setParameter("offset", reqPage.getOffset());
		
		if(reqData.getCountryCode()!="" && reqData.getCountryCode()!=null)
			queryEntity.setParameter("countryCode", reqData.getCountryCode());
		if(reqData.getYear()!="" && reqData.getYear()!=null)
			queryEntity.setParameter("year", reqData.getYear()+"%");
		if(reqData.getFrom()!="" && reqData.getFrom()!=null
				&& reqData.getTo()!="" && reqData.getTo()!=null) {
			queryEntity.setParameter("from", reqData.getFrom());
			queryEntity.setParameter("to", reqData.getTo());
		}
		
		List<PublicHolidaysResDto> resultList = queryEntity.getResultList();
		
		return resultList;
		
	}

}
