package com.financialadvisor.api.rest.dto;

import java.util.List;

public class PortfolioOptimizationRequestDTO {
	
	int riskLevel;
	double minAbsolutePercentageToChangeFor;
	
	PortfolioDTO portfolioDTO;
	
	
	public PortfolioOptimizationRequestDTO(
		int riskLevel, 
		ListWrapper<NameValuePairDTO> assetsList,
		double minAbsolutePercentageToChangeFor) 
	{
		super();
		this.riskLevel = riskLevel;
		this.minAbsolutePercentageToChangeFor = minAbsolutePercentageToChangeFor;		
		portfolioDTO = new PortfolioDTO(assetsList);
	}

	
	
	public int getRiskLevel() {
		return riskLevel;
	}


	public void setRiskLevel(int riskLevel) {
		this.riskLevel = riskLevel;
	}


	public PortfolioDTO getPortfolioDTO() {
		return portfolioDTO;
	}


	public void setPortfolioDTO(PortfolioDTO portfolioDTO) {
		this.portfolioDTO = portfolioDTO;
	}


	public double getMinAbsolutePercentageToChangeFor() {
		return minAbsolutePercentageToChangeFor;
	}


	public void setMinAbsolutePercentageToChangeFor(double minAbsolutePercentageToChangeFor) {
		this.minAbsolutePercentageToChangeFor = minAbsolutePercentageToChangeFor;
	}
		

	
}
