package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.Fields;
import com.my.kramarenko.taxService.db.dao.TypeDao;
import com.my.kramarenko.taxService.db.entity.Type;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static com.my.kramarenko.taxService.db.requestFields.SQL_SELECT_ALL_REPORT_TYPES;
import static com.my.kramarenko.taxService.db.requestFields.SQL_SELECT_REPORT_TYPE_BY_ID;

public class TypeManager implements TypeDao {
    private static final Logger LOG = Logger.getLogger(TypeManager.class);

    @Override
    public List<Type> getAllTypes() throws DBException {
        try (Connection con = DBManager.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL_REPORT_TYPES)) {
            List<Type> typeList = parseResultSet(rs);
            con.commit();
            return typeList;
        } catch (SQLException e) {
            String message = "Cannot obtain all types";
            LOG.error(message, e);
            throw new DBException(message, e);
        }
    }

    @Override
    public Type getType(String id) throws DBException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_SELECT_REPORT_TYPE_BY_ID);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            List<com.my.kramarenko.taxService.db.entity.Type> typeList = parseResultSet(rs);
            con.commit();
            return typeList.get(0);
        } catch (SQLException e) {
            String message = "Cannot obtain all types";
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
    private static List<Type> parseResultSet(ResultSet rs) throws SQLException {
        List<Type> result = new LinkedList<>();
        while (rs.next()) {
            Type type = new Type();
            type.setId(rs.getString(Fields.ENTITY_ID));
            type.setName(rs.getString(Fields.ENTITY_NAME));
            result.add(type);
        }
        return result;
    }
}
