package com.my.kramarenko.taxService.web.command.util;

import com.my.kramarenko.taxService.db.enums.Status;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReportUtilTest {

    @Test
    void getChosenStatusMap() {
        Map<Integer, Status> statusMap = getStatusMap();
        String[] chosedId = {"1", "3"};
        Map<Status, Boolean> resultMap = ReportUtil.getChosenStatusMap(statusMap, chosedId);
        assertEquals(statusMap.size(), resultMap.size());
        assertEquals(true, resultMap.get(statusMap.get(1)));
        assertEquals(true, resultMap.get(statusMap.get(3)));
        assertEquals(false, resultMap.get(statusMap.get(2)));
        assertEquals(false, resultMap.get(statusMap.get(4)));
    }

    private static Map<Integer, Status> getStatusMap() {
        Map<Integer, Status> statusMap = new HashMap<>();
        for (Status status : Status.values()) {
            statusMap.put(status.getId(), status);
        }
        return statusMap;
    }
}