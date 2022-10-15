package com.my.kramarenko.taxService.db.dto;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.entity.Type;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.entity.Status;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.db.mySQL.ReportManager;
import com.my.kramarenko.taxService.db.mySQL.UserManager;
import org.apache.log4j.Logger;

import java.util.*;

public class UserReportDTOBuilder {

    private static final Logger LOG = Logger.getLogger(UserReportDTOBuilder.class);

    public static List<ReportDTO> getAllUserReportsWithStatuses(User user, Map<Status, Boolean> statuses, Map<String, Type> typeMap) throws DBException {
        List<ReportDTO> reportsList = new ArrayList<>();
        ReportManager reportManager = DBManager.getInstance().getReportManager();
        List<Status> chosenStatuses = statuses.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .toList();
        List<ReportDTO> reports = ReportDTOBuilder
                .getReportsDTO(
                        reportManager.getUserReportsWithStatuses(user.getId(), chosenStatuses),
                        typeMap,
                        user
                );
        if (reports.size() > 0) {
            reportsList.addAll(reports);
        }
        return reportsList;
    }

    public static List<ReportDTO> getAllReportsWithStatuses(Map<Status, Boolean> statusMap, Map<String, Type> typeMap) throws DBException {
        List<ReportDTO> list = new ArrayList<>();
        List<User> users = new UserManager().getAllUsers();
        for (User user : users) {
            list.addAll(getAllUserReportsWithStatuses(user, statusMap, typeMap));
        }
        return list;
    }
}
