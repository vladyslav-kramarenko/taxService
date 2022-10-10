package com.my.kramarenko.taxService.db.dto;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.entity.Type;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.entity.Status;
import com.my.kramarenko.taxService.db.mySQL.ReportManager;
import com.my.kramarenko.taxService.db.mySQL.UserManager;
import org.apache.log4j.Logger;

import java.util.*;

public class UserReportDTOBuilder {

    private static final Logger LOG = Logger.getLogger(UserReportDTOBuilder.class);

    public static Map<User, List<ReportDTO>> getUserReportsWithStatuses(User user, Map<Status, Boolean> statuses, Map<String, Type> typeMap) throws DBException {
        Map<User, List<ReportDTO>> reportsList = new HashMap<>();
        ReportManager reportManager = new ReportManager();
        List<Status> chosenStatuses = statuses.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .toList();
        List<ReportDTO> reports = ReportDTOBuilder
                .getReportsDTO(
                        reportManager.getUserReportsWithStatuses(user.getId(), chosenStatuses),
                        typeMap
                );
        if (reports.size() > 0) {
            reportsList.put(user, reports);
        }
        return reportsList;
    }

    public static Map<User, List<ReportDTO>> getAllUserReportsWithStatuses(Map<Status, Boolean> statusMap, Map<String, Type> typeMap) throws DBException {
        Map<User, List<ReportDTO>> map = new HashMap<>();
        List<User> users = new UserManager().getAllUsers();
        for (User user : users) {
            map.put(user, getUserReportsWithStatuses(user, statusMap, typeMap).get(user));
        }
        return map;
    }
}
