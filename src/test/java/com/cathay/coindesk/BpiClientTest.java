package com.cathay.coindesk;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.cathay.coindesk.client.BpiClient;
import com.cathay.coindesk.client.bean.CurrentpriceBpiOut;

@DisplayName("BPI client")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BpiClientTest {
	
	@Autowired
	private BpiClient client;
	
	@Test
	public void getCurrentprice() {
		Map<String, CurrentpriceBpiOut> map = client.getCurrentprice();
	}

}
