package com.my.kramarenko.taxService.db.dto;

import com.my.kramarenko.taxService.db.dao.ReportDAO;
import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.db.entity.Type;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import org.apache.log4j.Logger;

import java.util.*;

public class UserReportDTOBuilder {

    private static final Logger LOG = Logger.getLogger(UserReportDTOBuilder.class);

//    public static List<ReportDTO> getUserReportsWithStatuses(User user, Map<Status, Boolean> statuses, Map<String, Type> typeMap) throws DBException {
//        List<ReportDTO> reportsList = new ArrayList<>();
//        ReportDAO reportManager = DBManager.getInstance().getReportDAO();
//
//        List<Status> chosenStatuses = statuses.entrySet()
//                .stream()
//                .filter(Map.Entry::getValue)
//                .map(Map.Entry::getKey)
//                .toList();
//
//        List<ReportDTO> reports = ReportDTOBuilder
//                .getReportsDTO(
//                        reportManager.getUserReportsWithStatuses(user.getId(), chosenStatuses),
//                        typeMap,
//                        user
//                );
//        if (reports.size() > 0) {
//            reportsList.addAll(reports);
//        }
//        return reportsList;
//    }
//
//    public static List<ReportDTO> getUserReportsWithStatusesWithTypeFilter(User user, Map<Status, Boolean> statuses, Map<String, Type> typeMap, String typePattern) throws DBException {
//        List<ReportDTO> reportsList = new ArrayList<>();
//        ReportDAO reportManager = DBManager.getInstance().getReportDAO();
//
//        List<Status> chosenStatuses = statuses.entrySet()
//                .stream()
//                .filter(Map.Entry::getValue)
//                .map(Map.Entry::getKey)
//                .toList();
//
//        List<ReportDTO> reports = ReportDTOBuilder
//                .getReportsDTO(
//                        reportManager.getUserReportsWithStatusesAndTypeFilter(user.getId(), chosenStatuses, typePattern),
//                        typeMap,
//                        user
//                );
//        if (reports.size() > 0) {
//            reportsList.addAll(reports);
//        }
//        return reportsList;
//    }

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

//    public static List<ReportDTO> getUsersReportsWithStatuses(Map<Status, Boolean> statusMap, Map<String, Type> typeMap, List<User> users) throws DBException {
//        List<ReportDTO> list = new ArrayList<>();
//        for (User user : users) {
//            list.addAll(getUserReportsWithStatuses(user, statusMap, typeMap));
//        }
//        return list;
//    }
public static List<ReportDTO> getUserReportsWithStatusesWithTypeFilter(Map<Status, Boolean> statusMap, Map<String, Type> typeMap,User user, String typePattern) throws DBException {
        List<User>oneUserList=new LinkedList<>();
        oneUserList.add(user);
        return getUsersReportsWithStatusesAndTypeFilter(statusMap,typeMap,oneUserList,typePattern);
}

    public static List<ReportDTO> getUsersReportsWithStatusesAndTypeFilter(Map<Status, Boolean> statusMap, Map<String, Type> typeMap, List<User> users, String typePattern) throws DBException {
        List<ReportDTO> reportsList = new ArrayList<>();
        ReportDAO reportManager = DBManager.getInstance().getReportDAO();

        List<Status> chosenStatuses = statusMap.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .toList();

        Map<User, List<Report>> userReports = reportManager.getUsersReportsWithStatusesAndTypeFilter(users, chosenStatuses, typePattern);

        for (User user : userReports.keySet()) {
            List<ReportDTO> reports = ReportDTOBuilder
                    .getReportsDTO(userReports.get(user), typeMap, user);
            if (reports.size() > 0) {
                reportsList.addAll(reports);
            }
        }


        return reportsList;
    }
}
