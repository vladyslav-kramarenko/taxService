package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.DbUtil;
import com.my.kramarenko.taxService.db.entity.Type;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static com.my.kramarenko.taxService.db.mySQL.requestFields.SQL_SELECT_ALL_REPORT_TYPES;
import static com.my.kramarenko.taxService.db.mySQL.requestFields.SQL_SELECT_REPORT_TYPE_BY_ID;

public class TypeManager {

    public static Type getType(Connection con, String id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_SELECT_REPORT_TYPE_BY_ID);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            List<Type> typeList = parseResultSet(rs);
            return typeList.get(0);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
        }
    }

    public static List<Type> getAllTypes(Connection con) throws SQLException {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL_REPORT_TYPES)) {
            return parseResultSet(rs);
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
            type.setLegalType(rs.getInt(Fields.LEGAL_TYPE_ID));
            result.add(type);
        }
        return result;
    }
}
