package com.my.kramarenko.taxService.db.dao;

import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.entity.UserDetails;
import com.my.kramarenko.taxService.exception.DBException;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    List<User> getAllUsers()throws DBException;

    List<User> getUsersByFilter(String userFilter)throws DBException;
    Optional<User> getUser(int userId) throws DBException;
    UserDetails getUserDetails(int userId) throws DBException;
    Optional<User> getUserByReportId(int reportId) throws DBException;
    void updateUser(User user,UserDetails userDetails) throws DBException;
    void updateUserPassword(User user) throws DBException;
    void setBanned(int userId, boolean banned) throws DBException;
    void updateUserRole(int userId, int roleId) throws DBException;
    void deleteUser(int userId) throws DBException;
    void addUser(User user, UserDetails userDetails) throws DBException;
    Optional<User> findUserByEmail(String email) throws DBException;

}
