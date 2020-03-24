package com.financialadvisor.portfolio;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;




@Service
public class PortfolioManager {
	
	
	RiskBasedRecommendation[] riskBasedRecommendations;
	
	
	public PortfolioManager() {
		setupAdvisedPortfolio();
	}

	
	
	
	/**
	 * 
	 * @param riskLevel  min = 1 and max =10;
	 * @return
	 */
	public RiskBasedRecommendation getPortfolioForRiskLevel(int riskLevel) {
		
		if (riskLevel < 1 || riskLevel > 10) {
			throw new IllegalArgumentException("Risk level min = 1 and max = 10 ");
		}
		
		return riskBasedRecommendations[riskLevel - 1];
	}
	
	
	
	public void optimizePortfolio(		
		Portfolio portfolio,
		RiskBasedRecommendation recommended,
		double minAbsolutePercentageToChangeFor) 
	throws Exception
	{			
		if (portfolio == null) {
			throw new IllegalArgumentException("portfolio cannot be null");
		}
		
		if (portfolio.totalPortfolioValue == 0) {
			throw new IllegalArgumentException("portfolio has zero asset value");
		}
		
		
		if (portfolio.totalPortfolioValue == 0) {
			throw new IllegalArgumentException("portfolio has zero asset value");
		}

		portfolio.optimize(minAbsolutePercentageToChangeFor, recommended);
	}
	
	
		
	private void setupAdvisedPortfolio()
	{
		riskBasedRecommendations = new RiskBasedRecommendation[10];
		
		riskBasedRecommendations[0] = new RiskBasedRecommendation( 1, 80,  20,  0,  0,  0);
		riskBasedRecommendations[1] = new RiskBasedRecommendation( 2, 70,  15, 15,  0,  0);
		riskBasedRecommendations[2] = new RiskBasedRecommendation( 3, 60,  15, 15, 10,  0);
		riskBasedRecommendations[3] = new RiskBasedRecommendation( 4, 50,  20, 20, 10,  0);
		riskBasedRecommendations[4] = new RiskBasedRecommendation( 5, 40,  20, 20, 20,  0);
		riskBasedRecommendations[5] = new RiskBasedRecommendation( 6, 35,  25,  5, 30,  5);
		riskBasedRecommendations[6] = new RiskBasedRecommendation( 7, 20,  25, 25, 25,  5);
		riskBasedRecommendations[7] = new RiskBasedRecommendation( 8, 10,  20, 40, 20, 10);
		riskBasedRecommendations[8] = new RiskBasedRecommendation( 9,  5,  15, 40, 25, 15);
		riskBasedRecommendations[9] = new RiskBasedRecommendation(10,  0,   5, 25, 30, 40);		
	}
	
}
