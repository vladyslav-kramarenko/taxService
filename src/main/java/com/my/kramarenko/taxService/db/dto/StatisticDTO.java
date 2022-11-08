package com.my.kramarenko.taxService.db.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class StatisticDTO implements Serializable {
    private String id;
    private String name;

    private Map<String, Integer> statistic;

    public StatisticDTO() {
        statistic = new HashMap<>();
    }

    @Override
    public String toString() {
        return "StatisticDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", " + statistic +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String companyName) {
        this.name = companyName;
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
