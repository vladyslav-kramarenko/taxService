package com.my.kramarenko.taxService.db.enums;


public enum SortType {

    TYPE, STATUS, DATE;
//	PRICE, PRICE_DESC;

    public String getName() {
        return name().toLowerCase();
    }
}