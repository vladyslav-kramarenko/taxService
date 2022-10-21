package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.dao.UserDao;
import com.my.kramarenko.taxService.db.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * Work with user entity in dataBase
 *
 * @author Vlad Kramarenko
 */
public class UserManager implements UserDao {

    private static final Logger LOG = Logger.getLogger(UserManager.class);

    /**
     * Search for all users
     *
     * @return list of users
     * @throws DBException
     */
    @Override
    public List<User> getAllUsers() throws DBException {
        try (Connection con = DBManager.getInstance().getConnection()) {
            con.setAutoCommit(true);
            return LowLevelUserManager.getAllUsers(con);
        } catch (SQLException e) {
            String message = "Cannot obtain all users";
            LOG.error(message, e);
            throw new DBException(message, e);
        }
    }

    /**
     * Search for all users with filter
     * @param userFilter part of user company name
     * @return list of users
     * @throws DBException
     */
    @Override
    public List<User> getUsersByFilter(String userFilter) throws DBException {
        try (Connection con = DBManager.getInstance().getConnection()) {
            con.setAutoCommit(true);
            return LowLevelUserManager.getAllUsersThatContainString(con, userFilter);
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
    @Override
    public Optional<User> getUser(int userId) throws DBException {
        try (Connection con = DBManager.getInstance().getConnection()) {
            con.setAutoCommit(true);
            return LowLevelUserManager.getUser(con, userId);
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
    @Override
    public void updateUser(User user) throws DBException {
        LOG.trace(user);
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            con.setAutoCommit(true);
            LowLevelUserManager.updateUser(con, user);
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
    @Override
    public void setBanned(int userId, boolean banned) throws DBException {
        LOG.trace("start update");
        try (Connection con = DBManager.getInstance().getConnection()) {
            LowLevelUserManager.setBanned(con, userId, banned);
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
    @Override
    public void updateUserRole(int userId, int roleId) throws DBException {
        LOG.trace("start update");
        try (Connection con = DBManager.getInstance().getConnection()) {
            con.setAutoCommit(true);
            LowLevelUserManager.updateUserRole(con, userId, roleId);
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
    @Override
    public void deleteUser(int userId) throws DBException {
        try (Connection con = DBManager.getInstance().getConnection()) {
            con.setAutoCommit(true);
            LowLevelUserManager.deleteUser(con, userId);
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
    @Override
    public void addUser(User user) throws DBException {
        try (Connection con = DBManager.getInstance().getConnection()) {
            LowLevelUserManager.addUser(con, user);
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
    @Override
    public Optional<User> findUserByEmail(String email) throws DBException {
        try (Connection con = DBManager.getInstance().getConnection()) {
            con.setAutoCommit(true);
            return LowLevelUserManager.findUserByEmail(con, email);
        } catch (SQLException ex) {
            String message = "Cannot obtain a user by its e-mail (" + email + ")";
            LOG.error(message, ex);
            throw new DBException(message, ex);
        }
    }
}
