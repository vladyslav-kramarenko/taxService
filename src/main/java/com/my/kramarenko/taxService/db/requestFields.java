package com.my.kramarenko.taxService.db;

public class requestFields {

    //User requests
    public static final String SQL_SELECT_ALL_USERS = "SELECT * FROM user";
    public static final String SQL_INSERT_INTO_USERS = "INSERT INTO user (email, password, first_name, last_name, role_id) \n"
            + "VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM user where id = ?";
    public static final String SQL_UPDATE_USER = "UPDATE user SET password=?, first_name=?, last_name=?,"
            + " role_id=?, phone=?, email=?" + "	WHERE id=?";
    public static final String SQL_UPDATE_USER_ROLE = "UPDATE user SET role_id=?"
            + "	WHERE id=?";
    public static final String SQL_DELETE_USER = "DELETE FROM user WHERE id= ?";
    //	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE login=?";
    public static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM user WHERE email=?";
}
