package com.financialadvisor.portfolio;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.financialadvisor.portfolio.Portfolio.AssetAllocationDifference;


public class PortfolioManagerTests {
	
	
	
	@Test
	void AssetAllocationDifferenceComparatorTest() {
		
		Portfolio portfolio = new Portfolio();
		
		
		AssetAllocationDifference pd1 = portfolio.new AssetAllocationDifference(ASSET_NAME.BOND, 30.0);
		AssetAllocationDifference pd2 = portfolio.new AssetAllocationDifference(ASSET_NAME.SMALL_CAP, 40.0);
		AssetAllocationDifference pd3 = portfolio.new AssetAllocationDifference(ASSET_NAME.LARGE_CAP, 20.0);
		
		assertThrows(IllegalArgumentException.class, () ->pd1.compareTo(null));
		
		// wrong object type
		assertThrows(IllegalArgumentException.class, () ->pd1.compareTo(new Integer(10))); 
		

		assertEquals( 0, pd1.compareTo(pd1));
		assertEquals(-1, pd1.compareTo(pd2));			
		assertEquals( 1, pd2.compareTo(pd1));		
		
		AssetAllocationDifference[] array = new AssetAllocationDifference[3];
		
		array[0] = pd1;
		array[1] = pd2;
		array[2] = pd3;
		
		AssetAllocationDifference[] sortedArray = new AssetAllocationDifference[3];
		
		sortedArray[0] = pd3;
		sortedArray[1] = pd1;
		sortedArray[2] = pd2;

		
		Arrays.sort(array);

		Assertions.assertArrayEquals(sortedArray, array);
		
		
	}
	
	
	@Test
	void EmptyPortfolioTest() throws Exception {
		
		PortfolioManager pm = new PortfolioManager();
		
		Portfolio portfolio = new Portfolio();	
		RiskBasedRecommendation recommended = pm.getPortfolioForRiskLevel(10);
		double minAbsoluteDifferenceToReallocate = 5;

		assertThrows(	IllegalArgumentException.class, 
						() ->pm.optimizePortfolio(portfolio, 
												  recommended, 
												  minAbsoluteDifferenceToReallocate)); 

	}
	
	
	
	@Test
	void PortfolioOptimizationTest01() throws Exception {
		
		PortfolioManager pm = new PortfolioManager();
		
		Portfolio portfolio1 = new Portfolio(10, 20, 30, 30, 10);	
		RiskBasedRecommendation recommended = pm.getPortfolioForRiskLevel(10);
		double minAbsoluteDifferenceToReallocate = 5;

		pm.optimizePortfolio(portfolio1, recommended, minAbsoluteDifferenceToReallocate);
		
		for (ASSET_NAME name : ASSET_NAME.values()) {
			
			double recommendedPercentage = recommended.getAssetPercentage(name);
			double actualPercentage = portfolio1.getAssetPercentage(name);
			double difference = recommendedPercentage - actualPercentage;
			
			Assertions.assertTrue( Math.abs(difference) < minAbsoluteDifferenceToReallocate);
		}
	}
		
		
	@Test
	void PortfolioOptimizationTest02() throws Exception {
		
		PortfolioManager pm = new PortfolioManager();
		RiskBasedRecommendation recommended = pm.getPortfolioForRiskLevel(5);
		double minAbsoluteDifferenceToReallocate = 2.5;				
		Portfolio portfolio = new Portfolio(10, 20, 30, 30, 10);
		
		
		pm.optimizePortfolio(portfolio, recommended, minAbsoluteDifferenceToReallocate);
		
		for (ASSET_NAME name : ASSET_NAME.values()) {
			
			double recommendedPercentage = recommended.getAssetPercentage(name);
			double actualPercentage = portfolio.getAssetPercentage(name);
			double difference = recommendedPercentage - actualPercentage;
			
			Assertions.assertTrue( Math.abs(difference) < minAbsoluteDifferenceToReallocate);
		}
	}
	
	
	
		
	@Test
	void PortfolioOptimizationTest03() throws Exception {
		
		PortfolioManager pm = new PortfolioManager();
		RiskBasedRecommendation recommended = pm.getPortfolioForRiskLevel(5);
		double minAbsoluteDifferenceToReallocate = 2.5;		
		Portfolio portfolio = new Portfolio(10, 22, 30, 29, 9);		
		
		pm.optimizePortfolio(portfolio, recommended, minAbsoluteDifferenceToReallocate);

		
		for (ASSET_NAME name : ASSET_NAME.values()) {
			
			double recommendedPercentage = recommended.getAssetPercentage(name);
			double actualPercentage = portfolio.getAssetPercentage(name);
			double difference = recommendedPercentage - actualPercentage;
			
			Assertions.assertTrue( Math.abs(difference) < minAbsoluteDifferenceToReallocate);
		}
	}
	

}
