package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.dao.ReportDao;
import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.xml.forms.ReportForm;
import com.my.kramarenko.taxService.xml.entity.TaxForm;
import com.my.kramarenko.taxService.xml.WriteXmlStAXController;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.*;
import java.util.List;

import static com.my.kramarenko.taxService.db.requestFields.*;

public class ReportManager implements ReportDao {
    private static final Logger LOG = Logger.getLogger(ReportManager.class);

    /**
     * Search for all reports
     *
     * @return list of Reports
     * @throws DBException
     */
    @Override
    public List<Report> getAllReports() throws DBException {
        List<Report> reportsList;
        try (Connection con = DBManager.getInstance().getConnection()) {
            con.setAutoCommit(true);
            reportsList = LowLevelReportManager.getAllReports(con);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DBException("Cannot obtain all reports", e);
        }
        return reportsList;
    }


    /**
     * Search reports by user id
     *
     * @param userId user id
     * @return list of Reports
     * @throws DBException
     */
    @Override
    public List<Report> getUserReports(int userId) throws DBException {
        List<Report> reportsList;
        try (Connection con = DBManager.getInstance().getConnection()) {
            con.setAutoCommit(true);
            reportsList = LowLevelReportManager.getUserReports(con, userId);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DBException("Cannot obtain reports by user id", e);
        }
        return reportsList;
    }

    /**
     * Search reports by user id
     *
     * @param userId user id
     * @return list of Reports
     * @throws DBException
     */
    @Override
    public List<Report> getUserReportsWithStatuses(int userId, List<Status> statuses) throws DBException {
        List<Report> reportsList;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            reportsList = LowLevelReportManager.getUserReportsWithStatuses(con, userId, statuses);
            con.commit();
        } catch (SQLException e) {
            DBManager.getInstance().rollback(con);
            LOG.error(e.getMessage());
            throw new DBException("Cannot obtain reports with statuses by user id", e);
        } finally {
            DBManager.close(con);
        }
        return reportsList;
    }

    @Override
    public Report getReport(int reportId) throws DBException {
        Report report;
        try (Connection con = DBManager.getInstance().getConnection()) {
            con.setAutoCommit(true);
            report = LowLevelReportManager.getReport(con, reportId);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DBException("Cannot get the report", e);
        }
        return report;
    }


    @Override
    public void editReport(Status status, int reportId, User user, TaxForm taxForm, ReportForm reportForm) throws DBException {
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
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
            DBManager.getInstance().rollback(con);
            String message = "Cannot edit the report";
            LOG.error(message + ":\n" + e.getMessage(), e);
            throw new DBException(message, e);
        } finally {
            DBManager.close(con);
        }
    }

    @Override
    public void updateReportStatus(int reportId, Status status, String comment) throws DBException {
        try (Connection con = DBManager.getInstance().getConnection();
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

    @Override
    public void updateReportStatus(int reportId, Status status) throws DBException {
        updateReportStatus(reportId, status, "");
    }

    /**
     * add new report
     *
     * @param user user of the report
     * @throws DBException
     */
    @Override
    public void addReport(Status status, User user, TaxForm taxForm, ReportForm reportForm) throws DBException {
        Report report = new Report();
        report.setTypeId(reportForm.getReportType());
        report.setStatusId(status.getId());
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            LowLevelReportManager.addReport(con, report);

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
            DBManager.getInstance().rollback(con);
            String message = "Cannot add a report";
            LOG.error(message + ":\n" + e.getMessage(), e);
            throw new DBException(message, e);
        } finally {
            DBManager.close(con);
        }
    }

    public void setReportStatusAndXmlPath(Connection con, Report report) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_SET_REPORT_STATUS_AND_XML_PATH);
            int k = 1;
            pstmt.setString(k++, report.getXmlPath());
            pstmt.setInt(k++, report.getStatusId());
            pstmt.setInt(k, report.getId());
            pstmt.executeUpdate();
        } finally {
            DBManager.close(pstmt);
        }
    }

    public void deleteReport(int id) throws DBException {
        Connection con;
        PreparedStatement preparedStatement;
        try {
            con = DBManager.getInstance().getConnection();
            preparedStatement = con.prepareStatement(SQL_DELETE_REPORT);
            Report report = LowLevelReportManager.getReport(con, id);
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
            DBManager.close(pstmt);
        }
    }
}
