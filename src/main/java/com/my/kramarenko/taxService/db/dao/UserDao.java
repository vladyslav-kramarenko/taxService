package com.my.kramarenko.taxService.db.dao;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> getAllUsers() throws DBException;

    List<User> getUsersByFilter(String userFilter) throws DBException;

    Optional<User> getUser(int userId) throws DBException;

    void updateUser(User user) throws DBException;

    void setBanned(int userId, boolean banned) throws DBException;

    void updateUserRole(int userId, int roleId) throws DBException;

    void deleteUser(int userId) throws DBException;

    void addUser(User user) throws DBException;

    Optional<User> findUserByEmail(String email)throws DBException;
}
