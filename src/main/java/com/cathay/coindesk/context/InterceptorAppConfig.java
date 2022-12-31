package com.cathay.coindesk.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class InterceptorAppConfig implements WebMvcConfigurer {
	
	@Value("${springdoc.api-docs.path}")
	private String api_docs_path;
	
	@Value("${springdoc.swagger-ui.path}")
	private String swagger_ui_path;
	
	@Value("${server.servlet.context-path}")
	private String contextpath;

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(api_docs_path);
		registry.addResourceHandler(swagger_ui_path);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/swagger-ui/index.html?url="+ contextpath + "/api-docs&validatorUrl=");
	}
}
