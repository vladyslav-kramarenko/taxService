package com.my.kramarenko.taxService.db.mySQL;

public class requestFields {

    //User requests
    public static final String SQL_SELECT_ALL_USERS = "SELECT * FROM user";
    public static final String SQL_SELECT_ALL_USERS_THAT_CONTAIN_STRING = "SELECT * FROM user WHERE LOCATE(?, company_name) > 0";
    public static final String SQL_SELECT_ALL_REPORTS = "SELECT * FROM report";
    public static final String SQL_SELECT_ALL_REPORT_TYPES = "SELECT * FROM type";
    public static final String SQL_SELECT_REPORT_TYPE_BY_ID = "SELECT * FROM type WHERE id=?";
    public static final String SQL_SELECT_USER_REPORTS = "SELECT * FROM report WHERE id IN " +
            "(SELECT report_id FROM user_report WHERE user_id=?)";
    public static final String SQL_SELECT_USER_REPORTS_WITH_STATUSES = "SELECT * FROM report " +
            "WHERE id IN (SELECT report_id FROM user_report WHERE user_id=?) " +
            "AND status_id IN (%)";
    public static final String SQL_SELECT_REPORT = "SELECT * FROM report WHERE id =?";
    public static final String SQL_INSERT_INTO_REPORTS = "INSERT INTO report (status_id,type_id,xml) VALUES(?, ?, ?)";
    public static final String SQL_SET_REPORT_STATUS_AND_XML_PATH = "UPDATE report SET xml=?, status_id=? WHERE id=?";
    public static final String SQL_UPDATE_REPORT_STATUS = "UPDATE report SET comment=?, status_id=? WHERE id=?";
    public static final String SQL_SET_USER_REPORT = "INSERT INTO user_report (user_id,report_id) VALUES(?,?)";
    public static final String SQL_DELETE_REPORT = "DELETE FROM report WHERE id = ?";
    public static final String SQL_INSERT_INTO_USERS = "INSERT INTO user (password, first_name, last_name,patronymic,code,company_name,is_individual, role_id,email,phone) \n"
            + "VALUES (?, ?, ?, ?,?, ?,?,?,?,?)";
    public static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM user where id = ?";
    public static final String SQL_UPDATE_USER = "UPDATE user SET " +
            "password=?, " +
            "first_name=?, " +
            "last_name=?, " +
            "patronymic=?, " +
            "code=?, " +
            "company_name=?, " +
            "is_individual=?, " +
            "role_id=?, " +
            "email=?, " +
            "phone=? " +
            "WHERE id=?";
    public static final String SQL_UPDATE_USER_ROLE = "UPDATE user SET role_id=?"
            + "	WHERE id=?";
    public static final String SQL_SET_USER_BANNED = "UPDATE user SET banned=?"
            + "	WHERE id=?";
    public static final String SQL_DELETE_USER = "DELETE FROM user WHERE id= ?";
    public static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM user WHERE email=?";
}
