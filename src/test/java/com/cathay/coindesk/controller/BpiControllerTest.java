package com.cathay.coindesk.controller;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cathay.coindesk.controller.bean.AddIn;
import com.cathay.coindesk.controller.bean.UpdIn;
import com.cathay.coindesk.util.JacksonUtil;

@DisplayName("BPI")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BpiControllerTest {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private static final String PATH = "/v1/bpi";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Order(1)
	@DisplayName("sync")
	@Test
	public void sync() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/sync"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andDo(MockMvcResultHandlers.print());;
	}
	
	@Order(2)
	@DisplayName("query")
	@Test
	public void query() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/query"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andDo(MockMvcResultHandlers.print());;
	}
	
	@Order(3)
	@DisplayName("add")
	@Test
	public void add() throws Exception {
		AddIn in = new AddIn();
		in.setCode("JPY");
		in.setName("日幣");
		in.setSymbol("Symbol");
		in.setRate("0.23");
		in.setDescription("Japanese");
		in.setRate_float(new BigDecimal("0.23"));
		mockMvc.perform(MockMvcRequestBuilders.post(PATH).contentType(MediaType.APPLICATION_JSON)
				.content(JacksonUtil.toJson(in))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists()).andDo(MockMvcResultHandlers.print());
	}
	
	@Order(4)
	@DisplayName("upd")
	@Test
	public void upd() throws Exception {
		UpdIn in = new UpdIn();
		in.setId(1l);
		in.setName("美金");
		mockMvc.perform(MockMvcRequestBuilders.put(PATH).contentType(MediaType.APPLICATION_JSON)
				.content(JacksonUtil.toJson(in))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists()).andDo(MockMvcResultHandlers.print());
	}
	
	@Order(5)
	@DisplayName("del")
	@Test
	public void del() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete(PATH).contentType(MediaType.APPLICATION_JSON).param("id", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Order(6)
	@DisplayName("client")
	@Test
	public void client() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/client"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andDo(MockMvcResultHandlers.print());;
	}
}
