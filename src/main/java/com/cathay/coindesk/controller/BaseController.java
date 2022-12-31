package com.cathay.coindesk.controller;

import javax.servlet.http.HttpServletRequest;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(name = "Authorization", in = SecuritySchemeIn.HEADER, type = SecuritySchemeType.APIKEY, description = "JWT format")
public class BaseController {
	
	public static final String OK = "OK";
	
	public static final String USER = "user";
	
	public String getUser(HttpServletRequest request) {
		return String.valueOf(request.getAttribute(USER));
	}
	
}
