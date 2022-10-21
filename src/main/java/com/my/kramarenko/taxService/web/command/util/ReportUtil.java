package com.my.kramarenko.taxService.web.command.util;

import com.my.kramarenko.taxService.db.enums.Status;

import java.util.Map;
import java.util.stream.Collectors;

public class ReportUtil {
    public static Map<Status, Boolean> getChosenStatusMap(Map<Integer, Status> statusMap, String[] chosedId) {
        Map<Status, Boolean> chosenStatuses;
        if (chosedId != null) {
            chosenStatuses = statusMap.values().stream().collect(Collectors.toMap(x -> x, x -> false));
            for (String s : chosedId) {
                int id = Integer.parseInt(s);
                chosenStatuses.put(statusMap.get(id), true);
            }
        } else {
            chosenStatuses = statusMap.values().stream().collect(Collectors.toMap(x -> x, x -> true));
        }
        return chosenStatuses;
    }
}
