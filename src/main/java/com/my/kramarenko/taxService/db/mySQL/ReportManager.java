package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.Fields;
import com.my.kramarenko.taxService.db.dao.ReportDao;
import com.my.kramarenko.taxService.db.dao.UserDao;
import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.ReportStatus;
import com.my.kramarenko.taxService.web.command.Path;
import com.my.kramarenko.taxService.xml.ReportForm;
import com.my.kramarenko.taxService.xml.TaxForm;
import com.my.kramarenko.taxService.xml.WriteXmlStAXController;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static com.my.kramarenko.taxService.db.requestFields.*;

public class ReportDb implements ReportDao {
    private static final Logger LOG = Logger.getLogger(UserDb.class);

    /**
     * Search for all reports
     *
     * @return list of Reports
     * @throws DBException
     */
    @Override
    public List<Report> getAllReports() throws DBException {
        List<Report> reportsList = null;
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_SELECT_ALL_REPORTS);
            reportsList = parseResultSet(rs);
            con.commit();
        } catch (SQLException e) {
            DBManager.getInstance().rollback(con);
            String message = "Cannot obtain all reports";
            LOG.error(message, e);
            throw new DBException(message, e);
        } finally {
            DBManager.getInstance().close(rs);
            DBManager.getInstance().close(stmt);
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
    public List<Report> getUserReports(int userId) throws DBException {
        List<Report> reportsList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
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
            report.setXml(rs.getString(Fields.REPORT_XML));
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
    public void addReport(User user, TaxForm taxForm, ReportForm reportForm) throws DBException {
        Report report = new Report();
        report.setTypeId(reportForm.getReportType());
        report.setStatusId(ReportStatus.CREATED.ordinal());
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            addReport(con, report);

            report.setXml(Path.XML_PATH + report.getId());

            WriteXmlStAXController writeXmlStAXController = new WriteXmlStAXController();
            writeXmlStAXController.writeToXML(taxForm, reportForm, report.getXmlPath());

            setReportXmlPath(con, report);

            con.commit();
        } catch (SQLException | XMLStreamException | IOException e) {
            DBManager.getInstance().rollback(con);
            String message = "Cannot add a report";
            LOG.error(message, e);
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
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            keys.next();
            report.setId(keys.getInt(1));
        } finally {
            DBManager.getInstance().close(pstmt);
        }
    }

    public void setReportXmlPath(Connection con, Report report) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_SET_REPORT_XML_PATH);
            int k = 1;
            pstmt.setString(k++, report.getXmlPath());
            pstmt.setInt(k++, report.getId());
            pstmt.executeUpdate();
        } finally {
            DBManager.getInstance().close(pstmt);
        }
    }
}
