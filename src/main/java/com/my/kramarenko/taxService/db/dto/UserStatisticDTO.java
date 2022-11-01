package com.my.kramarenko.taxService.db.dto;

import com.my.kramarenko.taxService.db.enums.Status;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserStatisticDTO implements Serializable {
    private int userId;
    private String companyName;

    private Map<String, Integer> statistic;

    public UserStatisticDTO() {
        statistic = new HashMap<>();
    }

    @Override
    public String toString() {
        return "UserStatisticDTO{" +
                "user_id=" + userId +
                ", company_name='" + companyName + '\'' +
                ", " + statistic +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Map<String, Integer> getStatistic() {
        return statistic;
    }

    public void setStatistic(Map<String, Integer> statistic) {
        this.statistic = statistic;
    }

    public void increaseStatistic(String status) {
        int value = 0;
        if (statistic.containsKey(status)) {
            value = statistic.get(status);
        }
        statistic.put(status, value + 1);
    }
}
