package com.cathay.coindesk.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cathay.coindesk.client.bean.Output;
import com.cathay.coindesk.ex.BpiException;
import com.cathay.coindesk.ex.BpiIllegalArgumentException;

@ControllerAdvice
public class Handler {
	
	private Logger log = LoggerFactory.getLogger(Handler.class);
	
	public static final String ERR_STR_NO_EXIST = "不存在或格式不正確!";

	@ExceptionHandler(Throwable.class)
	public @ResponseBody Output handle(Throwable e, HttpServletRequest request, HttpServletResponse response) {
		log.error("Handler Error!", e);
		if(response.getStatus() == HttpStatus.OK.value())
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		Output out = new Output();
		out.setStatus("fail");
		if(e instanceof BpiException || e instanceof BpiIllegalArgumentException) {
			out.setMsg(e.getMessage());
		}else if(e instanceof MethodArgumentTypeMismatchException) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			out.setMsg("參數" + ((MethodArgumentTypeMismatchException) e).getName() + ERR_STR_NO_EXIST);
		}else if(e instanceof MissingServletRequestParameterException) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			out.setMsg("參數" + ((MissingServletRequestParameterException) e).getParameterName() + ERR_STR_NO_EXIST);
		}else if(e instanceof MethodArgumentNotValidException) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			out.setMsg("參數" + ((MethodArgumentNotValidException) e).getBindingResult().getFieldError().getField() + ERR_STR_NO_EXIST);
		}else {
			out.setMsg("非預期錯誤!");
		}
		out.setErrorMsg(e.getMessage());
		return out;
	}
}
