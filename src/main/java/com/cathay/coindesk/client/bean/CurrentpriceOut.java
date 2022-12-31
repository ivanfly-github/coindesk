package com.cathay.coindesk.client.bean;

import java.util.Map;

import lombok.Data;

@Data
public class CurrentpriceOut {
	private CurrentpriceTimeOut time;
	private String disclaimer;
	private String chartName;
	private Map<String, CurrentpriceBpiOut> bpi;
}
