package com.financialadvisor.api.rest.dto;

import java.util.List;

public class ListWrapper<T> {

	private List<T> data;

	public ListWrapper() {}

	public ListWrapper(List<T> data) {
		this.data = data;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}

}
