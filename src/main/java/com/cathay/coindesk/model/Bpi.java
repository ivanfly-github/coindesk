package com.cathay.coindesk.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Entity
@Table(name = "bpi")
public class Bpi {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Schema(description = "幣別", example = "USD")
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
	private BigDecimal rate_float;
	
	@Schema(description = "更新時間", example = "2022-02-28 09:00:00")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime upd_datetime;
}
