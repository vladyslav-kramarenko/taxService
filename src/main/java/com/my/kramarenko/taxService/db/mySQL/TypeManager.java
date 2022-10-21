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
        try (Connection con = DBManager.getInstance().getConnection()) {
            con.setAutoCommit(true);
            return getAllTypes(con);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DBException("Cannot obtain all types", e);
        }
    }

    public List<Type> getAllTypes(Connection con) throws SQLException {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL_REPORT_TYPES)) {
            List<Type> typeList = parseResultSet(rs);
            return typeList;
        }
    }

    @Override
    public Type getType(String id) throws DBException {
        try (Connection con = DBManager.getInstance().getConnection()) {
            con.setAutoCommit(true);
            return getType(con, id);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new DBException("Cannot obtain the type with id " + id, e);
        }
    }

    public Type getType(Connection con, String id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_SELECT_REPORT_TYPE_BY_ID);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            List<com.my.kramarenko.taxService.db.entity.Type> typeList = parseResultSet(rs);
            return typeList.get(0);
        } finally {
            DBManager.close(rs);
            DBManager.close(pstmt);
        }
    }

    /**
     * Parse resultSet for select query
     *
     * @param rs ResultSet
     * @return list of types
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
