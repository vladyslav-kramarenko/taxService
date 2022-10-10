package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.Fields;
import com.my.kramarenko.taxService.db.dao.StatusDao;
import com.my.kramarenko.taxService.db.entity.Status;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.kramarenko.taxService.db.requestFields.*;

public class StatusManager implements StatusDao {
    private static final Logger LOG = Logger.getLogger(StatusManager.class);

    @Override
    public List<Status> getAllStatuses() throws DBException {
        try (Connection con = DBManager.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL_STATUSES)) {
            List<Status> statusList = parseResultSet(rs);
            con.commit();
            return statusList;
        } catch (SQLException e) {
            String message = "Cannot obtain all types";
            LOG.error(message, e);
            throw new DBException(message, e);
        }
    }

    @Override
    public Status getStatus(int id) throws DBException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_SELECT_STATUS_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            List<Status> statusList = parseResultSet(rs);
            con.commit();
            return statusList.get(0);
        } catch (SQLException e) {
            String message = "Cannot obtain status by id";
            LOG.error(message, e);
            throw new DBException(message, e);
        } finally {
            DBManager.getInstance().close(rs);
            DBManager.getInstance().close(pstmt);
            DBManager.getInstance().close(con);
        }
    }

    /**
     * Parse resultSet for select query
     *
     * @param rs ResultSEt
     * @return list of users
     * @throws SQLException
     */
    private static List<Status> parseResultSet(ResultSet rs) throws SQLException {
        List<Status> result = new ArrayList<>();
        while (rs.next()) {
            Status type = new Status();
            type.setId(rs.getInt(Fields.ENTITY_ID));
            type.setName(rs.getString(Fields.ENTITY_NAME));
            result.add(type);
        }
        return result;
    }
}
