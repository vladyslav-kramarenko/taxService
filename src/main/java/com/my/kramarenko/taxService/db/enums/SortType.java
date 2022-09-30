package com.my.kramarenko.taxService.db.enums;


public enum SortType {

	PRICE, PRICE_DESC;
	
	public String getName() {
		return name().toLowerCase();
	}
	
}