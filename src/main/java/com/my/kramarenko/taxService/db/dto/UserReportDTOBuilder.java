package com.my.kramarenko.taxService.db.dto;

import com.my.kramarenko.taxService.db.dao.ReportDAO;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.db.entity.Type;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import org.apache.log4j.Logger;

import java.util.*;

public class UserReportDTOBuilder {

    private static final Logger LOG = Logger.getLogger(UserReportDTOBuilder.class);

    public static List<ReportDTO> getAllUserReportsWithStatuses(User user, Map<Status, Boolean> statuses, Map<String, Type> typeMap) throws DBException {
        List<ReportDTO> reportsList = new ArrayList<>();
        ReportDAO reportManager = DBManager.getInstance().getReportDAO();

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

//    public static List<ReportDTO> getAllReportsWithStatuses(Map<Status, Boolean> statusMap, Map<String, Type> typeMap) throws DBException {
//        List<ReportDTO> list = new ArrayList<>();
//        List<User> users = new UserManager().getAllUsers();
//        for (User user : users) {
//            list.addAll(getAllUserReportsWithStatuses(user, statusMap, typeMap));
//        }
//        return list;
//    }

//    public static List<ReportDTO> getAllReportsWithStatusesWithUser(Map<Status, Boolean> statusMap, Map<String, Type> typeMap, String userFilter) throws DBException {
//        List<ReportDTO> list = new ArrayList<>();
//        List<User> users = new UserManager().getUsersByFilter(userFilter);
//        for (User user : users) {
//            list.addAll(getAllUserReportsWithStatuses(user, statusMap, typeMap));
//        }
//        return list;
//    }

    public static List<ReportDTO> getUsersReportsWithStatuses(Map<Status, Boolean> statusMap, Map<String, Type> typeMap, List<User> users) throws DBException {
        List<ReportDTO> list = new ArrayList<>();
        for (User user : users) {
            list.addAll(getAllUserReportsWithStatuses(user, statusMap, typeMap));
        }
        return list;
    }
}
