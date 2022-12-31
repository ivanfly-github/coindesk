package com.cathay.coindesk.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cathay.coindesk.client.bean.CurrentpriceOut;
import com.cathay.coindesk.client.bean.Output;
import com.cathay.coindesk.controller.bean.AddIn;
import com.cathay.coindesk.controller.bean.UpdIn;
import com.cathay.coindesk.model.Bpi;
import com.cathay.coindesk.service.BpiService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "匯率")
@RestController
@RequestMapping(path = "/v1/bpi")
public class BpiController extends BaseController {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BpiService service;
	
	@Operation(summary = "同步", responses = {
			@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Bpi.class)))),
			@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = Output.class))) }, security = @SecurityRequirement(name = "Authorization"))
	@GetMapping("/sync")
	public List<Bpi> sync(HttpServletRequest request) {
		service.sync();
		return service.query();
	}
	
	@Operation(summary = "列表", responses = {
			@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Bpi.class)))),
			@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = Output.class))) }, security = @SecurityRequirement(name = "Authorization"))
	@GetMapping("/query")
	public List<Bpi> query(HttpServletRequest request) {
		return service.query();
	}
	
	@Operation(summary = "新增", responses = {
			@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Bpi.class))),
			@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = Output.class))) }, security = @SecurityRequirement(name = "Authorization"))	
	@PostMapping
	public Bpi add(HttpServletRequest request, @RequestBody @Valid AddIn in) {
		return service.add(in);
	}
	
	@Operation(summary = "更新", responses = {
			@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Bpi.class))),
			@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = Output.class))) }, security = @SecurityRequirement(name = "Authorization"))
	@PutMapping
	public Bpi upd(HttpServletRequest request, @RequestBody @Valid UpdIn in) {
		return service.upd(in);
	}
	
	@Operation(summary = "刪除", responses = {
			@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Boolean.class))),
			@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = Output.class))) }, security = @SecurityRequirement(name = "Authorization"))
	@DeleteMapping
	public Boolean del(HttpServletRequest request, @RequestParam @Valid @NotNull Long id) {
		service.del(id);
		return true;
	}
	
	@Operation(summary = "client", responses = {
			@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CurrentpriceOut.class))),
			@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = Output.class))) }, security = @SecurityRequirement(name = "Authorization"))
	@GetMapping("/client")
	public CurrentpriceOut client(HttpServletRequest request) {
		return service.client();
	}
}
