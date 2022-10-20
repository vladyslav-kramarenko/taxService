package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.Fields;
import com.my.kramarenko.taxService.db.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.my.kramarenko.taxService.db.requestFields.*;

public class LowLevelUserManager {

    private static final Logger LOG = Logger.getLogger(UserManager.class);

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
            pstmt.executeUpdate();
            LOG.trace("user successfully updated");
        }
    }

    private static int fillUserFields(User user, PreparedStatement pstmt) throws SQLException {
        int k = 1;
        pstmt.setString(k++, user.getPassword());
        pstmt.setString(k++, user.getFirstName());
        pstmt.setString(k++, user.getLastName());
        pstmt.setString(k++, user.getPatronymic());
        pstmt.setString(k++, user.getCode());
        pstmt.setString(k++, user.getCompanyName());
        pstmt.setInt(k++, user.isIndividual() ? 1 : 0);
        LOG.info("isInd=" + (user.isIndividual() ? 1 : 0));
        pstmt.setInt(k++, user.getRoleId());
        LOG.info("roleId=" + user.getRoleId());
        pstmt.setString(k++, user.getEmail());
        pstmt.setString(k++, user.getPhone());
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
            DBManager.getInstance().close(rs);
            DBManager.getInstance().close(pstmt);
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
            user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
            user.setLastName(rs.getString(Fields.USER_LAST_NAME));
            user.setPatronymic(rs.getString(Fields.USER_PATRONYMIC));
//			user.setLogin(rs.getString(Fields.USER_LOGIN));
            user.setPassword(rs.getString(Fields.USER_PASSWORD));
            user.setCode(rs.getString(Fields.USER_CODE));
            user.setPhone(rs.getString(Fields.USER_PHONE));
            user.setEmail(rs.getString(Fields.USER_EMAIL));
            user.setCompanyName(rs.getString(Fields.USER_COMPANY_NAME));
            user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
            user.setBanned(rs.getInt(Fields.USER_IS_BANNED));
            user.setIndividual(rs.getInt(Fields.USER_IS_INDIVIDUAL));
            result.add(user);
        }
        return result;
    }

    public static User getUser(Connection con, int userId) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_SELECT_USER_BY_ID);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            return parseResultSet(rs).get(0);
        } finally {
            DBManager.getInstance().close(rs);
            DBManager.getInstance().close(pstmt);
        }
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
            DBManager.getInstance().close(rs);
            DBManager.getInstance().close(pstmt);
        }
    }

    public static void deleteUser(Connection con, int userId) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(SQL_DELETE_USER)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        }
    }
}