package com.financialadvisor.api.rest.controller;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.financialadvisor.api.rest.dto.ListWrapper;
import com.financialadvisor.api.rest.dto.NameValuePairDTO;
import com.financialadvisor.api.rest.dto.PortfolioDTO;
import com.financialadvisor.api.rest.dto.PortfolioOptimizationRequestDTO;
import com.financialadvisor.api.rest.dto.RiskBasedRecommendationDTO;
import com.financialadvisor.api.rest.service.PortfolioService;
import com.financialadvisor.exception.UserException;
import com.financialadvisor.portfolio.ASSET_NAME;
import com.financialadvisor.portfolio.Portfolio;
import com.financialadvisor.portfolio.RiskBasedRecommendation;

@RestController
@RequestMapping("/v1/api/portfolio")
@Validated
public class PortfolioController {
	
	Logger logger = LoggerFactory.getLogger(PortfolioController.class.getName());
	
	
	@Autowired
	PortfolioService portfolioService;
	
	
	@GetMapping("/risk/{riskNumber}")
	public ResponseEntity<RiskBasedRecommendationDTO> getPortfolioForRisk(
		@PathVariable("riskNumber") @NotNull @Min(1) @Max(10) int riskNumber) 
	{		
		RiskBasedRecommendationDTO recommendationDTO;
		ModelMapper mapper;
		try {
			RiskBasedRecommendation recommendation = portfolioService.getPortfolioForRiskLevel(riskNumber);
					
			recommendationDTO = new RiskBasedRecommendationDTO(recommendation);
			
			mapper = new ModelMapper();
			
			mapper.map(recommendationDTO, RiskBasedRecommendationDTO.class);
			return ResponseEntity.ok(mapper.map(recommendationDTO, RiskBasedRecommendationDTO.class));
			
		} catch (UserException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (Exception e) {			
			logger.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}
	
	

	@PostMapping("/optimize")
	public ResponseEntity<PortfolioDTO> optimizePortfolio(
		@RequestBody PortfolioOptimizationRequestDTO dto) 	
	{
		PortfolioDTO portfolioDTO = null;
		try {
			portfolioDTO = portfolioService.optimizePortfolio(dto);
			return ResponseEntity.ok(new ModelMapper().map(portfolioDTO, PortfolioDTO.class));
		} catch (UserException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (Exception e) {			
			logger.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	

	
	
	
	@PostMapping("/test2")
	public BodyBuilder test2(
		@RequestBody ListWrapper<NameValuePairDTO> wrapper) 	
	{
		List<NameValuePairDTO> list = wrapper.getData();
		return ResponseEntity.ok();
	}

	
	@PostMapping("/test")
	public BodyBuilder test(
		@RequestBody Login dto) 	
	{
				
		return ResponseEntity.ok();
	}

	
	public static class Login {

	    String username;
	    String password;
		public Login(String username, String password) {
			super();
			this.username = username;
			this.password = password;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}

	    
	    
	    //getters and setters
	}

}
