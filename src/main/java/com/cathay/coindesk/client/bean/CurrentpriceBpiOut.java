package com.cathay.coindesk.client.bean;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CurrentpriceBpiOut {
	private String code;
	private String symbol;
	private String rate;
	private String description;
	private BigDecimal rate_float;
}
