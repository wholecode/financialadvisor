package com.financialadvisor.api.rest.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.financialadvisor.portfolio.ASSET_NAME;
import com.financialadvisor.portfolio.RiskBasedRecommendation;

public class RiskBasedRecommendationDTO {

	List<NameValuePairDTO> assetsDTO;
	
	public RiskBasedRecommendationDTO(RiskBasedRecommendation recommendation) {
		
		assetsDTO = Arrays.stream(ASSET_NAME.values())
				   	.map(name -> 
				   		new NameValuePairDTO(	
				   				name.getDisplayName(), 
				   				recommendation.getAssetPercentage(name)))
				   	.collect(Collectors.toList());
	}
	
	

	public List<NameValuePairDTO> getAssetsDTO() {
		return assetsDTO;
	}

	public void setAssetsDTO(List<NameValuePairDTO> assetsDTO) {
		this.assetsDTO = assetsDTO;
	}
	
	
	

}
