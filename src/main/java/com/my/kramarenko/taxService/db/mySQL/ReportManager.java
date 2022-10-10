package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.Fields;
import com.my.kramarenko.taxService.db.dao.ReportDao;
import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.entity.Status;
import com.my.kramarenko.taxService.xml.ReportForm;
import com.my.kramarenko.taxService.xml.TaxForm;
import com.my.kramarenko.taxService.xml.WriteXmlStAXController;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Report> reportsList = null;
        try (Connection con = DBManager.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL_REPORTS)) {
            reportsList = parseResultSet(rs);
            con.commit();
        } catch (SQLException e) {
            String message = "Cannot obtain all reports";
            LOG.error(message, e);
            throw new DBException(message, e);
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
        List<Report> reportsList = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_SELECT_USER_REPORTS);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            reportsList = parseResultSet(rs);
            con.commit();
        } catch (SQLException e) {
            DBManager.getInstance().rollback(con);
            String message = "Cannot obtain reports by user id";
            LOG.error(message, e);
            throw new DBException(message, e);
        } finally {
            DBManager.getInstance().close(rs);
            DBManager.getInstance().close(pstmt);
            DBManager.getInstance().close(con);
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
        List<Report> reportsList = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getInstance().getConnection();
            LOG.trace("user id = " + userId);
            String parameters = statuses.stream().map(x -> "?").collect(Collectors.joining(","));
            LOG.trace("parameters: " + parameters);
            String sql = SQL_SELECT_USER_REPORTS_WITH_STATUSES.replace("%", parameters);
            LOG.trace(sql);

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);
            for (int i = 2; i < statuses.size() + 2; i++) {
                pstmt.setInt(i, statuses.get(i - 2).getId());
            }
            LOG.trace(pstmt);
            rs = pstmt.executeQuery();
            reportsList = parseResultSet(rs);
            con.commit();
        } catch (SQLException e) {
            DBManager.getInstance().rollback(con);
            String message = "Cannot obtain reports with statuses by user id";
            LOG.error(message, e);
            throw new DBException(message, e);
        } finally {
            DBManager.getInstance().close(rs);
            DBManager.getInstance().close(pstmt);
            DBManager.getInstance().close(con);
        }
        return reportsList;
    }

    @Override
    public Report getReport(int reportId) throws DBException {
        Report report = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_SELECT_REPORT);
            pstmt.setInt(1, reportId);
            rs = pstmt.executeQuery();
            report = parseResultSet(rs).get(0);
            con.commit();
        } catch (SQLException e) {
            String message = "Cannot get report";
            LOG.error(message, e);
            throw new DBException(message, e);
        } finally {
            DBManager.getInstance().close(rs);
            DBManager.getInstance().close(pstmt);
            DBManager.getInstance().close(con);
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
            DBManager.getInstance().close(con);
        }
    }

    @Override
    public void updateReportStatus(int reportId, Status status, String comment) throws DBException {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_REPORT_STATUS);) {
            int k = 1;
            pstmt.setString(k++, comment);
            pstmt.setInt(k++, status.getId());
            pstmt.setInt(k++, reportId);
            LOG.trace(pstmt);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            String message = "Cannot update the report's status";
            LOG.error(message + ":\n" + e.getMessage(), e);
            throw new DBException(message, e);
        }
    }

    /**
     * Parse resultSet for select query
     *
     * @param rs ResultSEt
     * @return list of users
     * @throws SQLException
     */
    private static List<Report> parseResultSet(ResultSet rs) throws SQLException {
        List<Report> result = new LinkedList<>();
        while (rs.next()) {
            Report report = new Report();
            report.setId(rs.getInt(Fields.ENTITY_ID));
            report.setDate(rs.getTimestamp(Fields.REPORT_DATE));
            report.setLastUpdate(rs.getTimestamp(Fields.REPORT_LAST_UPDATE));
            report.setXmlPath(rs.getString(Fields.REPORT_XML));
            report.setStatusId(rs.getInt(Fields.REPORT_STATUS_ID));
            report.setTypeId(rs.getString(Fields.REPORT_TYPE_ID));
            result.add(report);
        }
        return result;
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
            addReport(con, report);

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
            DBManager.getInstance().close(con);
        }
    }

    /**
     * Add new user
     *
     * @param con    database connection
     * @param report user to add
     * @throws SQLException
     */
    public void addReport(Connection con, Report report) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_INSERT_INTO_REPORTS,
                    Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setInt(k++, report.getStatusId());
            pstmt.setString(k++, report.getTypeId());
            pstmt.setString(k++, "");
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            keys.next();
            report.setId(keys.getInt(1));
        } finally {
            DBManager.getInstance().close(pstmt);
        }
    }

    public void setReportStatusAndXmlPath(Connection con, Report report) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_SET_REPORT_STATUS_AND_XML_PATH);
            int k = 1;
            pstmt.setString(k++, report.getXmlPath());
            pstmt.setInt(k++, report.getStatusId());
            pstmt.setInt(k++, report.getId());
            pstmt.executeUpdate();
        } finally {
            DBManager.getInstance().close(pstmt);
        }
    }


    public void setUserReport(Connection con, User user, Report report) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_SET_USER_REPORT);
            int k = 1;
            pstmt.setInt(k++, user.getId());
            pstmt.setInt(k++, report.getId());
            pstmt.executeUpdate();
        } finally {
            DBManager.getInstance().close(pstmt);
        }
    }
}
