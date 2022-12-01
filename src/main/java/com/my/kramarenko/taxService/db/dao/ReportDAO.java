package com.my.kramarenko.taxService.db.dao;

import com.my.kramarenko.taxService.db.dto.StatisticDTO;
import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.xml.entity.TaxForm;
import com.my.kramarenko.taxService.xml.forms.ReportForm;

import java.util.List;
import java.util.Map;

public interface ReportDAO {
    void addReport(Status status, User user, TaxForm taxForm, ReportForm reportForm) throws DBException;

    List<Report> getUserReportsWithStatuses(int userId, List<Status> statuses) throws DBException;

    List<Report> getUserReportsWithStatusesAndTypeFilter(int userId, List<Status> statuses, String typePattern) throws DBException;

    Map<User, List<Report>> getUsersReportsWithStatusesAndTypeFilter(List<User> users, List<Status> statuses, String typePattern) throws DBException;

    Report getReport(int reportId) throws DBException;

    List<StatisticDTO> getFilterUserReportStatistics(String pattern) throws DBException;

    List<StatisticDTO> getFilterReportStatistics(String pattern) throws DBException;

    void editReport(Status status, int reportId, TaxForm taxForm, ReportForm reportForm) throws DBException;

    void updateReportStatus(int reportId, Status status, String comment) throws DBException;

    void updateReportStatus(int reportId, Status status) throws DBException;

    void deleteReport(int id) throws DBException;
}
