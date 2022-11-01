package com.my.kramarenko.taxService.db.dao;

import com.my.kramarenko.taxService.db.dto.UserStatisticDTO;
import com.my.kramarenko.taxService.db.mySQL.ReportManager;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.db.DbUtil;
import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.xml.forms.ReportForm;
import com.my.kramarenko.taxService.xml.entity.TaxForm;
import com.my.kramarenko.taxService.xml.WriteXmlStAXController;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.my.kramarenko.taxService.db.mySQL.ReportManager.setReportStatusAndXmlPath;
import static com.my.kramarenko.taxService.db.mySQL.requestFields.*;

public class ReportDAO {
    private static final Logger LOG = Logger.getLogger(ReportDAO.class);

    private final DataSource ds;

    public ReportDAO(DataSource ds) {
        this.ds = ds;
    }
//    /**
//     * Search for all reports
//     *
//     * @return list of Reports
//     * @throws DBException
//     */
//
//    public List<Report> getAllReports() throws DBException {
//        List<Report> reportsList;
//        try (Connection con = ds.getConnection()) {
//            con.setAutoCommit(true);
//            reportsList = ReportManager.getAllReports(con);
//        } catch (SQLException e) {
//            LOG.error(e.getMessage());
//            throw new DBException("Cannot obtain all reports", e);
//        }
//        return reportsList;
//    }


//    /**
//     * Search reports by user id
//     *
//     * @param userId user id
//     * @return list of Reports
//     * @throws DBException
//     */
//
//    public List<Report> getUserReports(int userId) throws DBException {
//        List<Report> reportsList;
//        try (Connection con = ds.getConnection()) {
//            con.setAutoCommit(true);
//            reportsList = ReportManager.getUserReports(con, userId);
//        } catch (SQLException e) {
//            LOG.error(e.getMessage());
//            throw new DBException("Cannot obtain reports by user id", e);
//        }
//        return reportsList;
//    }

    /**
     * Search reports by user id
     *
     * @param userId user id
     * @return list of Reports
     * @throws DBException
     */

    public List<Report> getUserReportsWithStatuses(int userId, List<Status> statuses) throws DBException {
        List<Report> reportsList;
        Connection con = null;
        try {
            con = ds.getConnection();
            reportsList = ReportManager.getUserReportsWithStatuses(con, userId, statuses);
            con.commit();
        } catch (SQLException e) {
            DbUtil.rollback(con);
            LOG.error(e.getMessage());
            throw new DBException("Cannot obtain reports with statuses by user id", e);
        } finally {
            DbUtil.close(con);
        }
        return reportsList;
    }

    public List<Report> getUserReportsWithStatusesAndTypeFilter(int userId, List<Status> statuses, String typePattern) throws DBException {
        List<Report> reportsList;
        Connection con = null;
        try {
            con = ds.getConnection();
            if (typePattern == null || typePattern.length() == 0) {
                LOG.trace("type pattern is empty");
                reportsList = ReportManager.getUserReportsWithStatuses(con, userId, statuses);
            } else {
                LOG.trace("type pattern = " + typePattern);
                reportsList = ReportManager.getUserReportsWithStatusesAndTypeFilter(con, userId, statuses, typePattern);
            }
            con.commit();
        } catch (SQLException e) {
            DbUtil.rollback(con);
            LOG.error(e.getMessage());
            throw new DBException("Cannot obtain reports with statuses by user id", e);
        } finally {
            DbUtil.close(con);
        }
        return reportsList;
    }

    public Map<User, List<Report>> getUsersReportsWithStatusesAndTypeFilter(List<User> users, List<Status> statuses, String typePattern) throws DBException {
        Map<User, List<Report>> reportsMap = new HashMap<>();
        Connection con = null;
        try {
            con = ds.getConnection();
            for (User user : users) {
                List<Report> reportsList;
                if (typePattern == null || typePattern.length() == 0) {
                    LOG.trace("type pattern is empty");
                    reportsList = ReportManager.getUserReportsWithStatuses(con, user.getId(), statuses);
                } else {
                    LOG.trace("type pattern = " + typePattern);
                    reportsList = ReportManager.getUserReportsWithStatusesAndTypeFilter(con, user.getId(), statuses, typePattern);
                }
                reportsMap.put(user, reportsList);
            }
            con.commit();
        } catch (SQLException e) {
            DbUtil.rollback(con);
            LOG.error(e.getMessage());
            throw new DBException("Cannot obtain reports with statuses by user id", e);
        } finally {
            DbUtil.close(con);
        }
        return reportsMap;
    }


    public Report getReport(int reportId) throws DBException {
        Report report;
        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(true);
            report = ReportManager.getReport(con, reportId);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DBException("Cannot get the report", e);
        }
        return report;
    }

    public List<UserStatisticDTO> getFilterUserReportStatistics(String pattern) throws DBException {
        List<UserStatisticDTO> statistics;
        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(true);
            if (pattern == null || pattern.length() == 0) {
                LOG.trace("search pattern is empty");
                statistics = ReportManager.getAllUserReportStatistics(con);
            } else {
                LOG.trace("search pattern = " + pattern);
                statistics = ReportManager.getFilterUserReportStatistics(con, pattern);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DBException("Cannot get users statistics", e);
        }
        return statistics;
    }


    public void editReport(Status status, int reportId, TaxForm taxForm, ReportForm reportForm) throws DBException {
        Connection con = null;
        try {
            con = ds.getConnection();
            Report report = getReport(reportId);
            report.setStatusId(status.getId());

            //TODO
            report.setXmlPath(report.getId() + ".xml");
//            report.setXml(Path.XML_PATH + report.getId()+".xml");
            LOG.trace(report.getXmlPath());

            WriteXmlStAXController writeXmlStAXController = new WriteXmlStAXController();
            writeXmlStAXController.writeToXML(taxForm, reportForm, report.getXmlPath());

            setReportStatusAndXmlPath(con, report);

            con.commit();
        } catch (SQLException | XMLStreamException | IOException e) {
            DbUtil.rollback(con);
            String message = "Cannot edit the report";
            LOG.error(message + ":\n" + e.getMessage(), e);
            throw new DBException(message, e);
        } finally {
            DbUtil.close(con);
        }
    }

    public void updateReportStatus(int reportId, Status status, String comment) throws DBException {
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_REPORT_STATUS)) {
            int k = 1;
            pstmt.setString(k++, comment);
            pstmt.setInt(k++, status.getId());
            pstmt.setInt(k, reportId);
            LOG.trace(pstmt);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            String message = "Cannot update the report's status";
            LOG.error(message + ":\n" + e.getMessage(), e);
            throw new DBException(message, e);
        }
    }

    public void updateReportStatus(int reportId, Status status) throws DBException {
        updateReportStatus(reportId, status, "");
    }

    /**
     * add new report
     *
     * @param user user of the report
     * @throws DBException
     */
    public void addReport(Status status, User user, TaxForm taxForm, ReportForm reportForm) throws DBException {
        Report report = new Report();
        report.setTypeId(reportForm.getReportType());
        report.setStatusId(status.getId());
        Connection con = null;
        try {
            con = ds.getConnection();
            ReportManager.addReport(con, report);

            //TODO folder
            report.setXmlPath(report.getId() + ".xml");
//            report.setXml(Path.XML_PATH + report.getId()+".xml");
            LOG.trace(report.getXmlPath());

            WriteXmlStAXController writeXmlStAXController = new WriteXmlStAXController();
            writeXmlStAXController.writeToXML(taxForm, reportForm, report.getXmlPath());

            setReportStatusAndXmlPath(con, report);

            setUserReport(con, user, report);

            con.commit();
        } catch (SQLException | XMLStreamException | IOException e) {
            DbUtil.rollback(con);
            String message = "Cannot add a report";
            LOG.error(message + ":\n" + e.getMessage(), e);
            throw new DBException(message, e);
        } finally {
            DbUtil.close(con);
        }
    }


    public void deleteReport(int id) throws DBException {
        Connection con;
        PreparedStatement preparedStatement;
        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(SQL_DELETE_REPORT);
            Report report = ReportManager.getReport(con, id);
            if (report.getStatusId() == 3) {
                LOG.error("Can't delete accepted report");
                throw new IllegalStateException();
            } else {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                con.commit();
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DBException("Can't delete the report", e);
        }
    }


    public void setUserReport(Connection con, User user, Report report) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_SET_USER_REPORT);
            int k = 1;
            pstmt.setInt(k++, user.getId());
            pstmt.setInt(k, report.getId());
            pstmt.executeUpdate();
        } finally {
            DbUtil.close(pstmt);
        }
    }
}
