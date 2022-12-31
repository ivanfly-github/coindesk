package com.cathay.coindesk.client;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cathay.coindesk.client.bean.CurrentpriceBpiOut;
import com.cathay.coindesk.client.bean.CurrentpriceOut;

@Component
public class BpiClient {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final String URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

	@Autowired
	private RestTemplate restTemplate;

	public Map<String, CurrentpriceBpiOut> getCurrentprice() {
		ResponseEntity<CurrentpriceOut> res = restTemplate.getForEntity(URL, CurrentpriceOut.class);
		if (res.getStatusCodeValue() == 200) {
			return res.getBody().getBpi();
		}
		return null;
	}
	
	public CurrentpriceOut get() {
		ResponseEntity<CurrentpriceOut> res = restTemplate.getForEntity(URL, CurrentpriceOut.class);
		if (res.getStatusCodeValue() == 200) {
			return res.getBody();
		}
		return null;
	}
}
