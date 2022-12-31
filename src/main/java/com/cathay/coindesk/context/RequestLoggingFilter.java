package com.cathay.coindesk.context;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import antlr.StringUtils;

public class RequestLoggingFilter extends CommonsRequestLoggingFilter {
	
	/**
	   * Log each request and respponse with full Request URI, content payload and duration of the request in ms.
	   * @param request the request
	   * @param response the response
	   * @param filterChain chain of filters
	   * @throws ServletException
	   * @throws IOException
	   */
	  @Override
	  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

	    long startTime = System.currentTimeMillis();
	    
	    ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request, getMaxPayloadLength());
	    ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
	    
	    logger.debug("### RestController request begin ###");

		StringBuffer reqInfo = new StringBuffer().append(request.getMethod()).append(" ").append(request.getRequestURL());

		String queryString = request.getQueryString();
		if (queryString != null) {
			reqInfo.append("?").append(queryString);
		}

		if (request.getAuthType() != null) {
			reqInfo.append(", authType=").append(request.getAuthType());
		}
		if (request.getUserPrincipal() != null) {
			reqInfo.append(", principalName=").append(request.getUserPrincipal().getName());
		}

		logger.debug("### " + reqInfo);
	    
	    filterChain.doFilter(wrappedRequest, wrappedResponse);     // ======== This performs the actual request!
	    
	    long duration = System.currentTimeMillis() - startTime;
		if (org.apache.commons.lang3.StringUtils.isNotBlank(wrappedRequest.getContentType())
				&& wrappedRequest.getContentType().indexOf("multipart") != -1) {
			//multipart/form-data
			logger.debug("### Request Body: multipart/form-data");
			logger.debug("### RestController request end ###");
	    }else {
	    	String requestBody = createMessage(wrappedRequest, "", "");
	    	if (requestBody.length() > 0) {
	    		logger.debug("### Request Body: " + requestBody);
	    		logger.debug("### RestController request end ###");
	    	}
	    }
	    
	    this.logger.debug("### RestController response begin ###");
		this.logger.debug("returned status=" + response.getStatus() + " in " + duration + "ms");
	    this.logger.debug(getContentAsString(wrappedResponse));
	    this.logger.debug("### RestController response end ###");
	    wrappedResponse.copyBodyToResponse();  // IMPORTANT: copy content of response back into original response

	  }
	  
	private String getContentAsString(ContentCachingResponseWrapper wrapper) {
		if (wrapper != null) {
			byte[] buf = wrapper.getContentAsByteArray();
			if (buf.length > 0) {
				int length = Math.min(buf.length, getMaxPayloadLength());
				try {
					return new String(buf, 0, length, "UTF-8");
				}
				catch (UnsupportedEncodingException ex) {
					return "[unknown]";
				}
			}
		}
		return null;
	}
}
