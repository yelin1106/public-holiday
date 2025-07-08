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
		
		if(reqData.getCountryCode()!="" && reqData.getCountryCode()!=null) {
			query.append("and ph.countryCode = :countryCode ");
		}
		if(reqData.getYear()!="" && reqData.getYear()!=null) {
			query.append("and ph.date like :year ");
		}
		
		TypedQuery<Long> queryEntity = entityManager.createQuery(query.toString(), Long.class);
		
		if(reqData.getCountryCode()!="" && reqData.getCountryCode()!=null) queryEntity.setParameter("countryCode", reqData.getCountryCode());
		if(reqData.getYear()!="" && reqData.getYear()!=null) queryEntity.setParameter("year", reqData.getYear()+"%");
		
		long cnt = queryEntity.getSingleResult();
		
//		long cnt = entityManager
//				.createQuery(
//						"select count(*) " 
//						+ "from TbPublicHolidaysDto ph "
//						+ "left join TbCountryCodeDto cc "
//						+ "on ph.countryCode = cc.countryCode "
//						+ "where ph.countryCode = :countryCode "
//						+ "and ph.date like :year "
//						, Long.class
//						)
//				.setParameter("countryCode", countryCode)
//				.setParameter("year", year+"%")
//				.getSingleResult();
		
		return cnt;
	}
	
	public List<PublicHolidaysResDto> getCountryHolidays(PublicHolidaysReqDto reqData, PaginationDto reqPage) {
		
		List<PublicHolidaysResDto> resultList = entityManager
				.createQuery(
						"select ph.countryCode countryCode, cc.name countryName, ph.date date, ph.localName localName, ph.name name, ph.fixed fixed, ph.global global, ph.counties counties, ph.launchYear launchYear, ph.types types " 
						+ "from TbPublicHolidaysDto ph "
						+ "left join TbCountryCodeDto cc "
						+ "on ph.countryCode = cc.countryCode "
						+ "where ph.countryCode = :countryCode "
						+ "and ph.date like :year "
						+ "order by ph.date "
						+ "limit :limit "
						+ "offset :offset "
						, PublicHolidaysResDto.class
						)
				.setParameter("countryCode", reqData.getCountryCode())
				.setParameter("year", reqData.getYear()+"%")
				.setParameter("limit", reqPage.getLimit())
				.setParameter("offset", reqPage.getOffset())
				.getResultList();
		
		return resultList;
		
	}

}
