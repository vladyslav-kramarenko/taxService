package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.Fields;
import com.my.kramarenko.taxService.db.dao.UserDao;
import com.my.kramarenko.taxService.db.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static com.my.kramarenko.taxService.db.requestFields.*;

/**
 * Work with user entity in dataBase
 *
 * @author Vlad Kramarenko
 */
public class UserDb implements UserDao {

    private static final Logger LOG = Logger.getLogger(UserDb.class);

    /**
     * Search for all users
     *
     * @return list of users
     * @throws DBException
     */
    @Override
    public List<User> getAllUsers() throws DBException {
        List<User> usersList = null;
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_SELECT_ALL_USERS);
            usersList = parseResultSet(rs);
            con.commit();
        } catch (SQLException e) {
            DBManager.getInstance().rollback(con);
            String message = "Cannot obtain all users";
            LOG.error(message, e);
            throw new DBException(message, e);
        } finally {
            DBManager.getInstance().close(rs);
            DBManager.getInstance().close(stmt);
            DBManager.getInstance().close(con);
        }
        return usersList;
    }

    /**
     * Search user by id
     *
     * @param userId user id
     * @return user
     * @throws DBException
     */
    @Override
    public User getUser(int userId) throws DBException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con.setAutoCommit(true);
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_SELECT_USER_BY_ID);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            user = parseResultSet(rs).get(0);
        } catch (SQLException e) {
            String message = "Cannot obtain user by id";
            LOG.error(message, e);
            throw new DBException(message, e);
        } finally {
            DBManager.getInstance().close(rs);
            DBManager.getInstance().close(pstmt);
            DBManager.getInstance().close(con);
        }
        return user;
    }

    /**
     * Update user
     *
     * @param user user to update
     * @throws DBException
     */
    @Override
    public void updateUser(User user) throws DBException {
        Connection con = null;
        DBManager dbManager = DBManager.getInstance();
        try {
            con = dbManager.getConnection();
            updateUser(con, user);
            con.commit();
        } catch (SQLException e) {
            dbManager.rollback(con);
            String message = "Cannot update a user";
            LOG.error(message, e);
            throw new DBException(message, e);
        } finally {
            dbManager.close(con);
        }
    }

    /**
     * Update user role
     *
     * @param userId id of user to update
     * @param roleId new role id
     * @throws DBException
     */
    @Override
    public void updateUserRole(int userId, int roleId) throws DBException {
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            PreparedStatement pstmt = null;
            try {
                pstmt = con.prepareStatement(SQL_UPDATE_USER_ROLE);
                int k = 1;
                pstmt.setInt(k++, roleId);
                pstmt.setInt(k, userId);
                pstmt.executeUpdate();
            } finally {
                DBManager.getInstance().close(pstmt);
            }
            con.commit();
        } catch (SQLException e) {
            DBManager.getInstance().rollback(con);
            String message = "Cannot update a user role";
            LOG.error(message, e);
            throw new DBException(message, e);

        } finally {
            DBManager.getInstance().close(con);
        }
    }

    /**
     * Update user
     *
     * @param con  database connection
     * @param user user to update
     * @throws SQLException
     */
    private void updateUser(Connection con, User user) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_UPDATE_USER);
            int k = 1;
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, user.getFirstName());
            pstmt.setString(k++, user.getLastName());
            pstmt.setInt(k++, user.getRoleId());
            pstmt.setString(k++, user.getPhone());
            pstmt.setString(k++, user.getEmail());
            pstmt.setInt(k, user.getId());
            pstmt.executeUpdate();
        } finally {
            DBManager.getInstance().close(pstmt);
        }
    }

    /**
     * Delete user
     *
     * @param user user to delete
     * @throws DBException
     */
    @Override
    public void deleteUser(User user) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL_DELETE_USER);
            pstmt.setInt(1, user.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            String message = "Cannot delete a user";
            LOG.error(message, ex);
            throw new DBException(message, ex);
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
    private static List<User> parseResultSet(ResultSet rs) throws SQLException {
        LinkedList<User> result = new LinkedList<>();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt(Fields.ENTITY_ID));
            user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
            user.setLastName(rs.getString(Fields.USER_LAST_NAME));
//			user.setLogin(rs.getString(Fields.USER_LOGIN));
            user.setPassword(rs.getString(Fields.USER_PASSWORD));
            user.setPhone(rs.getString(Fields.USER_PHONE));
            user.setEmail(rs.getString(Fields.USER_EMAIL));
//			user.setCity(rs.getString(Fields.USER_CITY));
//			user.setAddress(rs.getString(Fields.USER_ADDRESS));
            user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
            result.add(user);
            System.out.println(user);
        }
        return result;
    }

    /**
     * add new user
     *
     * @param user user to add
     * @throws DBException
     */
    @Override
    public void addUser(User user) throws DBException {
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            addUser(con, user);
            con.commit();
        } catch (SQLException e) {
            DBManager.getInstance().rollback(con);
            String message = "Cannot create a user";
            LOG.error(message, e);
            throw new DBException(message, e);
        } finally {
            DBManager.getInstance().close(con);
        }
    }

    /**
     * Add new user
     *
     * @param con  database connection
     * @param user user to add
     * @throws SQLException
     */
    private void addUser(Connection con, User user) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_INSERT_INTO_USERS,
                    Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k++, user.getEmail());
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, user.getFirstName());
            pstmt.setString(k++, user.getLastName());
            pstmt.setInt(k++, user.getRoleId());
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            keys.next();
            user.setId(keys.getInt(1));
        } finally {
            DBManager.getInstance().close(pstmt);
        }
    }

    /**
     * Returns a user with the given login.
     *
     * @param email User e-mail.
     * @return User entity.
     */
    @Override
    public User findUserByEmail(String email) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(true);
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_EMAIL);
            pstmt.setString(1, email);
            LOG.debug(pstmt);
            rs = pstmt.executeQuery();
            List<User> users = parseResultSet(rs);
            if (users.size() > 0) {
                user = users.get(0);
                LOG.debug("Found user in DB: " + user);
            } else {
                LOG.debug("User not founded in DB: " + email);
            }
        } catch (SQLException ex) {
            String message = "Cannot obtain a user by its e-mail (" + email + ")";
            LOG.error(message, ex);
        } finally {
            DBManager.getInstance().close(rs);
            DBManager.getInstance().close(pstmt);
            DBManager.getInstance().close(con);
        }
        return user;
    }
}
