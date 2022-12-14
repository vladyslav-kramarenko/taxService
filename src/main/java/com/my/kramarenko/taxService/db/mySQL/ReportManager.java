package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.DbUtil;
import com.my.kramarenko.taxService.db.dto.StatisticDTO;
import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Status;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.my.kramarenko.taxService.db.mySQL.requestFields.*;

public class ReportManager {
    private static final Logger LOG = Logger.getLogger(ReportManager.class);

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
            DbUtil.close(rs);
            DbUtil.close(pstmt);
        }
        return report;
    }

    public static void deleteReport(Connection con, int reportId) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(SQL_DELETE_USER)) {
            pstmt.setInt(1, reportId);
            pstmt.executeUpdate();
        }
    }

    public static List<Report> getAllReports(Connection con) throws SQLException {
        List<Report> reportsList;
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL_REPORTS)) {
            reportsList = parseResultSet(rs);
        }
        return reportsList;
    }

    public static List<StatisticDTO> getFilterUserReportStatistics(Connection con, String pattern) throws SQLException {
        return getFilterStatistics(con, pattern, SQL_SELECT_FILTER_USERS_REPORTS_STATISTICS);
    }

    public static List<StatisticDTO> getFilterReportTypeStatistics(Connection con, String pattern) throws SQLException {
        return getFilterStatistics(con, pattern, SQL_SELECT_FILTER_REPORTS_STATISTICS);

    }

    public static List<StatisticDTO> getFilterStatistics(Connection con, String pattern, String sql) throws SQLException {
        List<StatisticDTO> reportsList;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();
            reportsList = parseStatisticDTOResultSet(rs);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
        }
        return reportsList;
    }

    public static List<StatisticDTO> getAllUserReportStatistics(Connection con) throws SQLException {
        return getStatistic(con, SQL_SELECT_ALL_USERS_REPORTS_STATISTICS);
    }

    public static List<StatisticDTO> getAllReportTypeStatistics(Connection con) throws SQLException {
        return getStatistic(con, SQL_SELECT_ALL_REPORTS_STATISTICS);
    }

    public static List<StatisticDTO> getStatistic(Connection con, String sql) throws SQLException {
        List<StatisticDTO> reportsList;
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            LOG.trace(sql);
            reportsList = parseStatisticDTOResultSet(rs);
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
            DbUtil.close(rs);
            DbUtil.close(pstmt);
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
            DbUtil.close(rs);
            DbUtil.close(pstmt);
        }
        return reportsList;
    }

    public static List<Report> getUserReportsWithStatusesAndTypeFilter(Connection con, int userId, List<Status> statuses, String typePattern) throws SQLException {
        List<Report> reportsList;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String parameters = statuses.stream().map(x -> "?").collect(Collectors.joining(","));
            String sql = SQL_SELECT_USER_REPORTS_WITH_STATUSES_AND_TYPE_FILTER.replace("%", parameters);

            pstmt = con.prepareStatement(sql);
            int index = 1;
            pstmt.setInt(index++, userId);
            for (int i = 0; i < statuses.size(); i++) {
                pstmt.setInt(index++, statuses.get(i).getId());
            }
            pstmt.setString(index, typePattern);
            LOG.trace(pstmt);
            rs = pstmt.executeQuery();
            reportsList = parseResultSet(rs);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
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
            report.setComment(rs.getString(Fields.REPORT_COMMENT));
            result.add(report);
        }
        return result;
    }

    /**
     * Parse resultSet for select query
     *
     * @param rs ResultSEt
     * @return list of users
     * @throws SQLException
     */
    private static List<StatisticDTO> parseStatisticDTOResultSet(ResultSet rs) throws SQLException {
        List<StatisticDTO> result = new LinkedList<>();
        StatisticDTO report = null;
        while (rs.next()) {
            String userId = rs.getString(Fields.ENTITY_ID);
            if (result.size() == 0 || !Objects.equals(userId, result.get(result.size() - 1).getId())) {
                report = new StatisticDTO();
                result.add(report);
            }
            report.setId(userId);
            report.setName(rs.getString(Fields.ENTITY_NAME));
            report.increaseStatistic(Status.getStatus(rs.getInt(Fields.REPORT_STATUS_ID)).getName().toUpperCase());
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
            DbUtil.close(pstmt);
        }
    }

    public static void setReportStatusAndXmlPath(Connection con, Report report) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_SET_REPORT_STATUS_AND_XML_PATH);
            int k = 1;
            pstmt.setString(k++, report.getXmlPath());
            pstmt.setInt(k++, report.getStatusId());
            pstmt.setInt(k, report.getId());
            pstmt.executeUpdate();
        } finally {
            DbUtil.close(pstmt);
        }
    }


    public static void setUserReport(Connection con, User user, Report report) throws SQLException {
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
