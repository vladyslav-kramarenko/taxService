package com.my.kramarenko.taxService.db.dao;

import com.my.kramarenko.taxService.db.entity.UserDetails;
import com.my.kramarenko.taxService.db.mySQL.UserManager;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.db.entity.User;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * Work with user entity in dataBase
 *
 * @author Vlad Kramarenko
 */
public class UserDAO {

    DataSource ds;

    public UserDAO(DataSource ds) {
        this.ds = ds;
    }

    private static final Logger LOG = Logger.getLogger(UserDAO.class);

    /**
     * Search for all users
     *
     * @return list of users
     * @throws DBException
     */

    public List<User> getAllUsers() throws DBException {
        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(true);
            return UserManager.getAllUsers(con);
        } catch (SQLException e) {
            String message = "Cannot obtain all users";
            LOG.error(message, e);
            throw new DBException(message, e);
        }
    }

    /**
     * Search for all users with filter
     *
     * @param userFilter part of user company name
     * @return list of users
     * @throws DBException
     */

    public List<User> getUsersByFilter(String userFilter) throws DBException {
        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(true);
            return UserManager.getAllUsersThatContainString(con, userFilter);
        } catch (SQLException e) {
            String message = "Cannot obtain all users";
            LOG.error(message, e);
            throw new DBException(message, e);
        }
    }

    /**
     * Search user by id
     *
     * @param userId user id
     * @return user
     * @throws DBException
     */

    public Optional<User> getUser(int userId) throws DBException {
        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(true);
            return UserManager.getUser(con, userId);
        } catch (SQLException e) {
            String message = "Cannot obtain user by id";
            LOG.error(message, e);
            throw new DBException(message, e);
        }
    }

    /**
     * Search user details by id
     *
     * @param userId user id
     * @return user details
     * @throws DBException
     */

    public UserDetails getUserDetails(int userId) throws DBException {
        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(true);
            return UserManager.getUserDetailsById(con, userId);
        } catch (SQLException e) {
            String message = "Cannot obtain user details by id";
            LOG.error(message, e);
            throw new DBException(message, e);
        }
    }


    public Optional<User> getUserByReportId(int reportId) throws DBException {
        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(true);
            return UserManager.getUserByReportId(con, reportId);
        } catch (SQLException e) {
            String message = "Cannot obtain user by id";
            LOG.error(message, e);
            throw new DBException(message, e);
        }
    }

    /**
     * Update user
     *
     * @param user user to update
     * @throws DBException
     */

    public void updateUser(User user,UserDetails userDetails) throws DBException {
        LOG.trace(user);
        try (Connection con = ds.getConnection()) {
            UserManager.updateUser(con, user);
            UserManager.updateUserDetails(con,userDetails);
            con.commit();
        } catch (SQLException e) {
            String message = "Cannot update a user";
            LOG.error(message, e);
            throw new DBException(message, e);
        }
    }


    /**
     * Update user
     *
     * @param user user to update
     * @throws DBException
     */

    public void updateUserPassword(User user) throws DBException {
        LOG.trace(user);
        try (Connection con = ds.getConnection()) {
            UserManager.updateUser(con, user);
            con.commit();
        } catch (SQLException e) {
            String message = "Cannot update a user";
            LOG.error(message, e);
            throw new DBException(message, e);
        }
    }

    /**
     * Update user role
     *
     * @param userId id of user to update
     * @param banned is user banned
     * @throws DBException
     */

    public void setBanned(int userId, boolean banned) throws DBException {
        LOG.trace("start update");
        try (Connection con = ds.getConnection()) {
            UserManager.setBanned(con, userId, banned);
            con.commit();
        } catch (SQLException e) {
            String message = "Cannot update user's role";
            LOG.error(message, e);
            throw new DBException(message, e);
        }
    }

    /**
     * Update user role
     *
     * @param userId id of user to update
     * @param roleId new role id
     * @throws DBException
     */

    public void updateUserRole(int userId, int roleId) throws DBException {
        LOG.trace("start update");
        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(true);
            UserManager.updateUserRole(con, userId, roleId);
        } catch (SQLException e) {
            String message = "Cannot update user's role";
            LOG.error(message, e);
            throw new DBException(message, e);
        }
    }


    /**
     * Delete user
     *
     * @param userId id of user to delete
     * @throws DBException
     */

    public void deleteUser(int userId) throws DBException {
        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(true);
            UserManager.deleteUser(con, userId);
        } catch (SQLException ex) {
            String message = "Cannot delete a user";
            LOG.error(message, ex);
            throw new DBException(message, ex);
        }
    }

    /**
     * add new user
     *
     * @param user user to add
     * @throws DBException
     */

    public void addUser(User user, UserDetails userDetails) throws DBException {
        try (Connection con = ds.getConnection()) {
            UserManager.addUser(con, user);
            userDetails.setUserId(user.getId());
            UserManager.addUserDetails(con,userDetails);
            con.commit();
        } catch (SQLException e) {
            String message = "Cannot create a user";
            LOG.error(message, e);
            throw new DBException(message, e);
        }
    }

    /**
     * Returns a user with the given login.
     *
     * @param email User e-mail.
     * @return User entity.
     */

    public Optional<User> findUserByEmail(String email) throws DBException {
        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(true);
            return UserManager.findUserByEmail(con, email);
        } catch (SQLException ex) {
            String message = "Cannot obtain a user by its e-mail (" + email + ")";
            LOG.error(message, ex);
            throw new DBException(message, ex);
        }
    }
}
