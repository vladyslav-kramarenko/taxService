package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.DbUtil;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.entity.UserDetails;
import com.my.kramarenko.taxService.db.mySQL.dao.UserDAOMySQL;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.my.kramarenko.taxService.db.mySQL.requestFields.*;

public class UserManager {

    private static final Logger LOG = Logger.getLogger(UserDAOMySQL.class);

    /**
     * @param con  database connection
     * @param user user to add
     * @throws SQLException throwable exception
     */
    public static void addUser(Connection con, User user) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_INTO_USERS,
                Statement.RETURN_GENERATED_KEYS)) {
            fillUserFields(user, pstmt);
            LOG.trace(pstmt);
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            keys.next();
            user.setId(keys.getInt(1));
        }
    }

    /**
     * @param con         database connection
     * @param userDetails userDetails to add
     * @throws SQLException throwable exception
     */
    public static void addUserDetails(Connection con, UserDetails userDetails) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_INTO_USER_DETAILS,
                Statement.RETURN_GENERATED_KEYS)) {
            int k = fillUserDetailsFields(userDetails, pstmt);
            pstmt.setInt(k, userDetails.getUserId());
            LOG.trace(pstmt);
            pstmt.executeUpdate();
        }
    }

    /**
     * Update user
     *
     * @param con  database connection
     * @param user user to update
     * @throws SQLException
     */
    public static void updateUser(Connection con, User user) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_USER)) {
            int k = fillUserFields(user, pstmt);
            pstmt.setInt(k, user.getId());
            System.out.println(pstmt);
            pstmt.executeUpdate();
            LOG.trace("user successfully updated");
        }
    }

    /**
     * Update user details
     *
     * @param con         database connection
     * @param userDetails user details to update
     * @throws SQLException
     */
    public static void updateUserDetails(Connection con, UserDetails userDetails) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_USER_DETAILS)) {
            int k = fillUserDetailsFields(userDetails, pstmt);
            pstmt.setInt(k, userDetails.getUserId());
            System.out.println(pstmt);
            pstmt.executeUpdate();
            LOG.trace("user details were successfully updated");
        }
    }

    private static int fillUserFields(User user, PreparedStatement pstmt) throws SQLException {
        int k = 1;
        pstmt.setString(k++, user.getPassword());
        pstmt.setString(k++, user.getCode());
        pstmt.setString(k++, user.getCompanyName());
        pstmt.setInt(k++, user.getLegalType());
        pstmt.setInt(k++, user.getRoleId());
        pstmt.setString(k++, user.getEmail());
        return k;
    }

    private static int fillUserDetailsFields(UserDetails userDetails, PreparedStatement pstmt) throws SQLException {
        int k = 1;
        pstmt.setString(k++, userDetails.getFirstName());
        pstmt.setString(k++, userDetails.getLastName());
        pstmt.setString(k++, userDetails.getPatronymic());
        pstmt.setString(k++, userDetails.getPhone());
        return k;
    }


    public static void updateUserRole(Connection con, int userId, int roleId) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_USER_ROLE)) {
            int k = 1;
            pstmt.setInt(k++, roleId);
            pstmt.setInt(k, userId);
            LOG.trace(pstmt);
            pstmt.executeUpdate();
        }
    }

    public static void setBanned(Connection con, int userId, boolean banned) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SET_USER_BANNED)) {
            int k = 1;
            pstmt.setInt(k++, banned ? 1 : 0);
            pstmt.setInt(k, userId);
            LOG.trace(pstmt);
            pstmt.executeUpdate();
        }
    }

    public static Optional<User> findUserByEmail(Connection con, String email) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_EMAIL);
            pstmt.setString(1, email);
            LOG.debug(pstmt);
            rs = pstmt.executeQuery();
            List<User> userList = parseResultSet(rs);
            if (userList.size() > 0)
                return Optional.of(userList.get(0));
            else return Optional.empty();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
        }
    }

    /**
     * Parse resultSet for select query
     *
     * @param rs ResultSEt
     * @return list of users
     * @throws SQLException
     */
    private static List<User> parseResultSet(ResultSet rs) throws SQLException {
        LinkedList<User> result = new LinkedList<>();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt(Fields.ENTITY_ID));
//            user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
//            user.setLastName(rs.getString(Fields.USER_LAST_NAME));
//            user.setPatronymic(rs.getString(Fields.USER_PATRONYMIC));
//            user.setPhone(rs.getString(Fields.USER_PHONE));
//			user.setLogin(rs.getString(Fields.USER_LOGIN));
            user.setPassword(rs.getString(Fields.USER_PASSWORD));
            user.setCode(rs.getString(Fields.USER_CODE));
            user.setEmail(rs.getString(Fields.USER_EMAIL));
            user.setCompanyName(rs.getString(Fields.USER_COMPANY_NAME));
            user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
            user.setBanned(rs.getInt(Fields.USER_IS_BANNED));
            user.setLegalType(rs.getInt(Fields.LEGAL_TYPE_ID));
            result.add(user);
        }
        return result;
    }


    public static Optional<User> getUser(Connection con, int userId) throws SQLException {
        return getUserById(con, SQL_SELECT_USER_BY_ID, userId);
    }


    private static Optional<User> getUserById(Connection con, String SQL, int id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            List<User> list = parseResultSet(rs);
            return list.size() > 0 ? Optional.ofNullable(list.get(0)) : Optional.empty();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
        }
    }

    public static UserDetails getUserDetailsById(Connection con, int id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_SELECT_USER_DETAILS_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            UserDetails userDetails = new UserDetails();
            if (rs.next()) {
                userDetails.setUserId(rs.getInt(Fields.USER_ID));
                userDetails.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
                userDetails.setLastName(rs.getString(Fields.USER_LAST_NAME));
                userDetails.setPatronymic(rs.getString(Fields.USER_PATRONYMIC));
                userDetails.setPhone(rs.getString(Fields.USER_PHONE));
            }
            return userDetails;
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
        }
    }

    public static Optional<User> getUserByReportId(Connection con, int reportId) throws SQLException {
        return getUserById(con, SQL_SELECT_USER_BY_REPORT_ID, reportId);
    }


    public static List<User> getAllUsers(Connection con) throws SQLException {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL_USERS)) {
            return parseResultSet(rs);
        }
    }

    public static List<User> getAllUsersThatContainString(Connection con, String partOfUserCompany) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_SELECT_ALL_USERS_THAT_CONTAIN_STRING);
            pstmt.setString(1, partOfUserCompany);
            LOG.trace(pstmt);
            rs = pstmt.executeQuery();
            return parseResultSet(rs);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
        }
    }

    public static void deleteUser(Connection con, int userId) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(SQL_DELETE_USER)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        }
    }
}