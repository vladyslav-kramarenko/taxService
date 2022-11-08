package com.my.kramarenko.taxService.web;

/**
 * Path holder (jsp pages, controller commands).
 *
 * @author Vlad Kramarenko
 */
public final class Path {

    // pages
    // NOT CONTROLLED
    public static final String PAGE_LOGIN = "/WEB-INF/jsp/notControlled/login.jsp";
    public static final String PAGE_INFO = "/WEB-INF/jsp/notControlled/info.jsp";
    public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/notControlled/error_page.jsp";
    public static final String PAGE_REGISTRATION = "/WEB-INF/jsp/notControlled/registration.jsp";
    // COMMON
    public static final String PAGE_REPORT = "/WEB-INF/jsp/common/editReport.jsp";
    public static final String PAGE_USER_REPORT_LIST = "/WEB-INF/jsp/common/reportList.jsp";
    public static final String PAGE_RESET_PASSWORD = "/WEB-INF/jsp/common/resetPassword.jsp";
    public static final String PAGE_SETTINGS = "/WEB-INF/jsp/common/settings.jsp";
    // ADMIN
    public static final String ADMIN_REPORT = "/WEB-INF/jsp/common/showReport.jsp";
    public static final String PAGE_ALL_USERS = "/WEB-INF/jsp/admin/all_users.jsp";
    public static final String PAGE_EDIT_USER = "/WEB-INF/jsp/admin/userSettings.jsp";
    // INSPECTOR
    public static final String PAGE_STATISTIC = "/WEB-INF/jsp/inspector/userStatistics.jsp";
    public static final String PAGE_REPORT_STATISTIC = "/WEB-INF/jsp/inspector/reportStatistic.jsp";
    // COMMANDS
    public static final String COMMAND_LOGIN = "controller?command=login";
    public static final String COMMAND_LOAD_XML = "controller?command=loadXML";
    public static final String COMMAND_REGISTRATION = "controller?command=registration";
    public static final String COMMAND_ALL_USERS = "controller?command=allUsers";
    public static final String COMMAND_INFO = "controller?command=info";
    public static final String COMMAND_SETTINGS = "controller?command=viewSettings";
    public static final String COMMAND_EDIT_USER = "controller?command=editUser";
    public static final String COMMAND_REPORT = "controller?command=editReport";
    public static final String COMMAND_REPORT_LIST = "controller?command=reportList";
    public static final String COMMAND_USER_STATISTIC = "controller?command=statistic";
    public static final String COMMAND_REPORT_STATISTIC = "controller?command=reportStatistic";
    public static final String COMMAND_DELETE_REPORT = "controller?command=deleteReport";
    //    OTHER
    public static final String XML_PATH = "/files/";
    public static final String DEFAULT_PAGE = PAGE_INFO;
}




