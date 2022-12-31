package com.cathay.coindesk.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {
	
	public static LocalDateTime getLocalDateTime() {
		return LocalDateTime.now(ZoneId.of("Asia/Taipei"));
	}
	
	public static LocalDate getLocalDate() {
		return LocalDate.now(ZoneId.of("Asia/Taipei"));
	}
}
