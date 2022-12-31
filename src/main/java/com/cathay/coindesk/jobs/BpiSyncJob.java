package com.cathay.coindesk.jobs;

import java.time.Duration;
import java.time.LocalDateTime;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.cathay.coindesk.service.BpiService;

@Component
public class BpiSyncJob extends QuartzJobBean {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BpiService service;
	
	@Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		log.info("匯率同步開始..");
		LocalDateTime s = LocalDateTime.now();
		
		service.sync();
		
		LocalDateTime e = LocalDateTime.now();
		log.info("匯率資料完成，共花費" + Duration.between(s, e).getSeconds() + "秒");
	}
}
