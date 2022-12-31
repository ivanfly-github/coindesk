package com.cathay.coindesk;

import java.util.Collections;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.cathay.coindesk.context.RequestLoggingFilter;
import com.cathay.coindesk.context.RestTemplateLoggingInterceptor;
import com.cathay.coindesk.jobs.BpiSyncJob;

@SpringBootApplication
public class CoindeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoindeskApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(20000);
		clientHttpRequestFactory.setReadTimeout(20000);
		//有使用攔截器，要使用緩存工廠，不然會取不到response body
		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(clientHttpRequestFactory));
		restTemplate.getInterceptors().add(new RestTemplateLoggingInterceptor());
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		restTemplate.getMessageConverters().add(converter); 
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
	   return restTemplate;
	}
	
    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
    	RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter();
        requestLoggingFilter.setIncludeClientInfo(true);
        requestLoggingFilter.setIncludeHeaders(true);
        requestLoggingFilter.setIncludeQueryString(true);
        requestLoggingFilter.setIncludePayload(true);
        requestLoggingFilter.setMaxPayloadLength(10000);
        return requestLoggingFilter;
    }
    
	@Bean
	public JobDetail bpiSyncJobDetail() {
		return JobBuilder.newJob().ofType(BpiSyncJob.class).storeDurably().build();
	}

	@Bean
	public Trigger bpiSyncJobTrigger() {
		return TriggerBuilder.newTrigger().forJob(bpiSyncJobDetail())
				.withSchedule(SimpleScheduleBuilder.repeatHourlyForever()).build();
	}
}
