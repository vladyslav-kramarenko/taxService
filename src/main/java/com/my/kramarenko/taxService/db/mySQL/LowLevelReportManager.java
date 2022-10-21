package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.Fields;
import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.enums.Status;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.my.kramarenko.taxService.db.requestFields.*;

public class LowLevelReportManager {
    private static final Logger LOG = Logger.getLogger(LowLevelReportManager.class);

    public static Report getReport(Connection con, int reportId) throws SQLException {
        Report report;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_SELECT_REPORT);
            pstmt.setInt(1, reportId);
            rs = pstmt.executeQuery();
            report = parseResultSet(rs).get(0);
        } finally {
            DBManager.close(rs);
            DBManager.close(pstmt);
        }
        return report;
    }

    public static List<Report> getAllReports(Connection con) throws SQLException {
        List<Report> reportsList;
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL_REPORTS)) {
            reportsList = parseResultSet(rs);
        }
        return reportsList;
    }

    /**
     * @param con    Connection
     * @param userId user id
     * @return list of Reports
     * @throws SQLException
     */
    public static List<Report> getUserReports(Connection con, int userId) throws SQLException {
        List<Report> reportsList;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_SELECT_USER_REPORTS);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            reportsList = parseResultSet(rs);
        } finally {
            DBManager.close(rs);
            DBManager.close(pstmt);
        }
        return reportsList;
    }

    public static List<Report> getUserReportsWithStatuses(Connection con, int userId, List<Status> statuses) throws SQLException {
        List<Report> reportsList;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String parameters = statuses.stream().map(x -> "?").collect(Collectors.joining(","));
            String sql = SQL_SELECT_USER_REPORTS_WITH_STATUSES.replace("%", parameters);

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);
            for (int i = 2; i < statuses.size() + 2; i++) {
                pstmt.setInt(i, statuses.get(i - 2).getId());
            }
            LOG.trace(pstmt);
            rs = pstmt.executeQuery();
            reportsList = parseResultSet(rs);
        } finally {
            DBManager.close(rs);
            DBManager.close(pstmt);
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
            report.setLastUpdate(rs.getTimestamp(Fields.REPORT_LAST_UPDATE));
            report.setXmlPath(rs.getString(Fields.REPORT_XML));
            report.setStatusId(rs.getInt(Fields.REPORT_STATUS_ID));
            report.setTypeId(rs.getString(Fields.REPORT_TYPE_ID));
            result.add(report);
        }
        return result;
    }

    /**
     * Add new user
     *
     * @param con    database connection
     * @param report user to add
     * @throws SQLException
     */
    public static void addReport(Connection con, Report report) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_INSERT_INTO_REPORTS,
                    Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setInt(k++, report.getStatusId());
            pstmt.setString(k++, report.getTypeId());
            pstmt.setString(k, "");
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            keys.next();
            report.setId(keys.getInt(1));
        } finally {
            DBManager.close(pstmt);
        }
    }

//    public static void setReportStatusAndXmlPath(Connection con, Report report) throws SQLException {
//        PreparedStatement pstmt = null;
//        try {
//            pstmt = con.prepareStatement(SQL_SET_REPORT_STATUS_AND_XML_PATH);
//            int k = 1;
//            pstmt.setString(k++, report.getXmlPath());
//            pstmt.setInt(k++, report.getStatusId());
//            pstmt.setInt(k, report.getId());
//            pstmt.executeUpdate();
//        } finally {
//            DBManager.getInstance().close(pstmt);
//        }
//    }

//    public static void setUserReport(Connection con, User user, Report report) throws SQLException {
//        PreparedStatement pstmt = null;
//        try {
//            pstmt = con.prepareStatement(SQL_SET_USER_REPORT);
//            int k = 1;
//            pstmt.setInt(k++, user.getId());
//            pstmt.setInt(k, report.getId());
//            pstmt.executeUpdate();
//        } finally {
//            DBManager.getInstance().close(pstmt);
//        }
//    }
}
