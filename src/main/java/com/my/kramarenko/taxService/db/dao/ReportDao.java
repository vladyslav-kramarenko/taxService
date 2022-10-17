package com.my.kramarenko.taxService.db.dao;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.entity.Status;
import com.my.kramarenko.taxService.xml.forms.ReportForm;
import com.my.kramarenko.taxService.xml.entity.TaxForm;

import java.util.List;

public interface ReportDao {
    void updateReportStatus(int reportId, Status status) throws DBException;

    void addReport(Status status, User user, TaxForm taxForm, ReportForm reportForm) throws DBException;

    List<Report> getAllReports() throws DBException;

    List<Report> getUserReports(int userId) throws DBException;

    List<Report> getUserReportsWithStatuses(int userId, List<Status> statuses) throws DBException;

    Report getReport(int reportId) throws DBException;

    void editReport(Status status,int reportId, User user, TaxForm taxForm, ReportForm reportForm) throws DBException;

    void updateReportStatus(int reportId, Status status, String comment) throws DBException;

}
