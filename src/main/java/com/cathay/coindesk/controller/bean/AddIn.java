package com.cathay.coindesk.controller.bean;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddIn {
	@Schema(description = "幣別", example = "USD")
	@NotBlank
	private String code;
	
	@Schema(description = "幣別名稱", example = "美金")
	private String name;
	
	@Schema(description = "符號", example = "&#36;")
	private String symbol;
	
	@Schema(description = "匯率字串(含千位符號)", example = "19,503.1713")
	private String rate;
	
	@Schema(description = "敘述", example = "United States Dollar")
	private String description;
	
	@Schema(description = "匯率", example = "19503.1713")
	@NotNull
	private BigDecimal rate_float;
}
