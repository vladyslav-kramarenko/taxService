package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.dao.ReportTypeDao;
import com.my.kramarenko.taxService.db.entity.Report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.my.kramarenko.taxService.db.requestFields.SQL_SELECT_ALL_REPORTS;

public class ReportTypeManager implements ReportTypeDao {

    @Override
    public List<ReportType> getAllReportTypes() throws DBException {
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
}
