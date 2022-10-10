package com.my.kramarenko.taxService.db.dao;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> getAllUsers() throws DBException;

    User getUser(int userId) throws DBException;

    void updateUser(User user) throws DBException;

    void updateUserRole(int userId, int roleId) throws DBException;

    void deleteUser(User user) throws DBException;

    void addUser(User user) throws DBException;

    Optional<User> findUserByEmail(String email)throws DBException;
}
