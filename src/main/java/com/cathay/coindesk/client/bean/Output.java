package com.cathay.coindesk.client.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Output {
	@Schema(description = "狀態(fail / error / ok)", example = "ok")
	private String status;
	@Schema(description = "訊息", example = "我是訊息")
	private String msg;
	@Schema(description = "失敗訊息", example = "我是訊息")
	private String errorMsg;
}
