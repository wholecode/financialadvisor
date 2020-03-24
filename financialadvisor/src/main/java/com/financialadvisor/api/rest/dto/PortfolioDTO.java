package com.financialadvisor.api.rest.dto;

import java.util.LinkedList;
import java.util.List;

import com.financialadvisor.exception.UserException;
import com.financialadvisor.portfolio.ASSET_NAME;
import com.financialadvisor.portfolio.Portfolio;

public class PortfolioDTO {
	
	
	double bond;
	double largeCap;
	double midCap;
	double foreign;
	double smallCap;
		
	List<NameValuePairDTO> assetsDTO;
	
	
	
	public PortfolioDTO(	
		double bond,
		double largeCap,
		double midCap,
		double foreign,
		double smallCap)
	{
		this.bond = bond;
		this.largeCap = largeCap;
		this.midCap = midCap;
		this.foreign = foreign;
		this.smallCap = smallCap;		
	}
	
	
	
	public PortfolioDTO(ListWrapper<NameValuePairDTO> data) {
		
		assetsDTO  = data.getData();
	}
	
	
	
	public PortfolioDTO(Portfolio portfolio) {
		
		assetsDTO = new LinkedList<>();
		
		for (ASSET_NAME name : ASSET_NAME.values()) {
			
			NameValuePairDTO nameValueDTO = new NameValuePairDTO(name.getDisplayName(), portfolio.getAssetValue(name));
			assetsDTO.add(nameValueDTO);
		}
	}
	
	
	public Portfolio convertToPortfolio() {
		
		Portfolio portfolio = new Portfolio();
		
		for (NameValuePairDTO dto : assetsDTO) {
			
			String name = dto.getName();
			ASSET_NAME assetName;
			
			try {
				assetName = ASSET_NAME.valueOf(name);
			}
			catch (Exception e) {
				throw new UserException("Illegal asset name: " + name);
			}
			
			if (dto.getNumber() < 0) {
				throw new UserException("Asset value cannot be less than zero");
			}
			
			portfolio.setAssetValue(assetName, dto.getNumber());
		}
		
		return portfolio;
	}


	public List<NameValuePairDTO> getAssetsDTO() {
		return assetsDTO;
	}


	public void setAssetsDTO(List<NameValuePairDTO> assetsDTO) {
		this.assetsDTO = assetsDTO;
	}

	
	
	
}
