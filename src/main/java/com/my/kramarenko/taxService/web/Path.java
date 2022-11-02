package com.my.kramarenko.taxService.web;

/**
 * Path holder (jsp pages, controller commands).
 *
 * @author Vlad Kramarenko
 */
public final class Path {

    // pages

    public static final String XML_PATH = "/files/";
//    public static final String PAGE_ALL_USER_PRODUCTS = "/WEB-INF/jsp/admin/ordersProducts.jsp";

    public static final String PAGE_LOGIN = "/WEB-INF/jsp/login.jsp";
    public static final String PAGE_INFO = "/WEB-INF/jsp/info.jsp";
    public static final String PAGE_REPORT = "/WEB-INF/jsp/common/report.jsp";
    public static final String PAGE_USER_REPORT_LIST = "/WEB-INF/jsp/common/reportList.jsp";
    public static final String DEFAULT_PAGE = PAGE_INFO;
    public static final String PAGE_REGISTRATION = "/WEB-INF/jsp/registration.jsp";
    public static final String PAGE_RESET_PASSWORD = "/WEB-INF/jsp/common/resetPassword.jsp";
    public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
    public static final String PAGE_SETTINGS = "/WEB-INF/jsp/common/settings.jsp";

    public static final String PAGE_ALL_USERS = "/WEB-INF/jsp/admin/all_users.jsp";
    public static final String PAGE_EDIT_USER = "/WEB-INF/jsp/admin/userSettings.jsp";
    public static final String PAGE_STATISTICS = "/WEB-INF/jsp/inspector/statistics.jsp";

    // commands
    public static final String COMMAND_LOGIN = "controller?command=login";
    public static final String COMMAND_LOAD_XML = "controller?command=loadXML";
    public static final String COMMAND_REGISTRATION = "controller?command=registration";
    public static final String COMMAND_ALL_USERS = "controller?command=allUsers";
    public static final String COMMAND_INFO = "controller?command=info";
    public static final String COMMAND_SETTINGS = "controller?command=viewSettings";
    public static final String COMMAND_EDIT_USER = "controller?command=editUser";

    public static final String COMMAND_REPORT = "controller?command=editReport";
    public static final String COMMAND_REPORT_LIST = "controller?command=reportList";
    public static final String COMMAND_STATISTICS = "controller?command=statistics";
    public static final String COMMAND_DELETE_REPORT = "controller?command=deleteReport";
}




