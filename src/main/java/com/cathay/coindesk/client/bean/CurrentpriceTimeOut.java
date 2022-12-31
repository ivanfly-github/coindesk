package com.cathay.coindesk.client.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CurrentpriceTimeOut {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM d, yyyy HH:mm:ss zzz", locale = "en-US")
	private Date updated;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
	private Date updatedISO;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM d, yyyy 'at' HH:mm zzz", locale = "en-US")
	private Date updateduk;
}
