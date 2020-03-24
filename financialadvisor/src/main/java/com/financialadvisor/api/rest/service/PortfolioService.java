package com.financialadvisor.api.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financialadvisor.api.rest.dto.PortfolioDTO;
import com.financialadvisor.api.rest.dto.PortfolioOptimizationRequestDTO;
import com.financialadvisor.portfolio.Portfolio;
import com.financialadvisor.portfolio.PortfolioManager;
import com.financialadvisor.portfolio.RiskBasedRecommendation;


@Service
public class PortfolioService {

	
	@Autowired
	private PortfolioManager portfolioManager;
	
	
	
	public RiskBasedRecommendation getPortfolioForRiskLevel(int riskLevel) {
		
		return portfolioManager.getPortfolioForRiskLevel(riskLevel);
	}
	
	
	
	public PortfolioDTO optimizePortfolio(PortfolioOptimizationRequestDTO dto) 
	throws Exception 
	{		
		RiskBasedRecommendation recommended = portfolioManager.getPortfolioForRiskLevel(dto.getRiskLevel());
		
		Portfolio portfolio = dto.getPortfolioDTO().convertToPortfolio();
		
		portfolioManager.optimizePortfolio(	portfolio, 
											recommended, 
											dto.getMinAbsolutePercentageToChangeFor());
		
		return new PortfolioDTO(portfolio);
	}

}
