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

    public static final String SQL_SELECT_USER_REPORTS_WITH_STATUSES_AND_TYPE_FILTER = "SELECT * FROM report " +
            "WHERE id IN (SELECT report_id FROM user_report WHERE user_id=?) " +
            "AND status_id IN (%) " +
            "AND type_id IN (SELECT type.id FROM type WHERE LOCATE(?, type.name) > 0)";

    public static final String SQL_SELECT_ALL_USERS_REPORTS_STATISTICS = "SELECT user.id, user.company_name as name, report.status_id\n" +
            "FROM report " +
            "JOIN user_report ON report.id=user_report.report_id " +
            "JOIN user ON user.id=user_report.user_id " +
            "order by user.id";

    public static final String SQL_SELECT_ALL_REPORTS_STATISTICS = "SELECT type.id, type.name, report.status_id\n" +
            "FROM report " +
            "JOIN type ON report.type_id=type.id " +
            "order by type.id";

    public static final String SQL_SELECT_FILTER_USERS_REPORTS_STATISTICS = "SELECT user.id, user.company_name as name, report.status_id\n" +
            "FROM report \n" +
            "JOIN user_report ON report.id=user_report.report_id " +
            "JOIN user ON user.id=user_report.user_id " +
            "WHERE LOCATE(?, user.company_name) > 0 " +
            "order by user.id";

    public static final String SQL_SELECT_FILTER_REPORTS_STATISTICS = "SELECT type.id, type.name, report.status_id\n" +
            "FROM report \n" +
            "JOIN type ON report.type_id=type.id " +
            "WHERE LOCATE(?, type.name) > 0 " +
            "order by type.id";
    public static final String SQL_SELECT_REPORT = "SELECT * FROM report WHERE id =?";
    public static final String SQL_INSERT_INTO_REPORTS = "INSERT INTO report (status_id,type_id,xml) VALUES(?, ?, ?)";
    public static final String SQL_SET_REPORT_STATUS_AND_XML_PATH = "UPDATE report SET xml=?, status_id=? WHERE id=?";
    public static final String SQL_UPDATE_REPORT_STATUS = "UPDATE report SET comment=?, status_id=? WHERE id=?";
    public static final String SQL_SET_USER_REPORT = "INSERT INTO user_report (user_id,report_id) VALUES(?,?)";
    public static final String SQL_DELETE_REPORT = "DELETE FROM report WHERE id = ?";
    public static final String SQL_INSERT_INTO_USERS = "INSERT INTO user (password,code,company_name,legal_type_id, role_id,email) \n"
            + "VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SQL_INSERT_INTO_USER_DETAILS = "INSERT INTO user_details (first_name, last_name,patronymic,phone,user_id) \n"
            + "VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM user where id = ?";
    public static final String SQL_SELECT_USER_DETAILS_BY_ID = "SELECT * FROM user_details where user_id = ?";
    public static final String SQL_SELECT_USER_BY_REPORT_ID = "SELECT * FROM user WHERE id IN (SELECT DISTINCT user_id FROM user_report WHERE report_id = ?)";
    public static final String SQL_UPDATE_USER = "UPDATE user SET " +
            "password=?, " +
            "code=?, " +
            "company_name=?, " +
            "legal_type_id=?, " +
            "role_id=?, " +
            "email=? " +
            "WHERE id=?";

    public static final String SQL_UPDATE_USER_DETAILS = "UPDATE user_details SET " +
            "first_name=?, " +
            "last_name=?, " +
            "patronymic=?, " +
            "phone=? " +
            "WHERE user_id=?";
    public static final String SQL_UPDATE_USER_ROLE = "UPDATE user SET role_id=?"
            + "	WHERE id=?";
    public static final String SQL_SET_USER_BANNED = "UPDATE user SET banned=?"
            + "	WHERE id=?";
    public static final String SQL_DELETE_USER = "DELETE FROM user WHERE id= ?";
    public static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM user WHERE email=?";
}
