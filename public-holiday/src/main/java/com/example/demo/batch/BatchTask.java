package com.example.demo.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.api.ApiService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BatchTask {
	
	@Autowired
	private ApiService apiService;
	
	/* @Scheduled(cron = "0 0/10 * * * *") */
	@Scheduled(cron = "0 0 1 2 1 ?")
	public void mainJob() {
		try {
			log.info("Batch Start");
			apiService.getPublicHolidays(2);
			log.info("Batch End");
		}  catch (Exception e) {
			log.info("Batch Exception Message: {}", e.getMessage());
		}
	}
	
}
