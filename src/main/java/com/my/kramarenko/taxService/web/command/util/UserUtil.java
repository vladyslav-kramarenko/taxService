package com.my.kramarenko.taxService.web.command.util;

import com.my.kramarenko.taxService.db.dao.UserDAO;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class UserUtil {
    public static void setUserFields(User editableUser, HttpServletRequest request) {
        editableUser.setEmail(request.getParameter("email"));
        editableUser.setIndividual(request.getParameter("is_individual"));
        editableUser.setCode(request.getParameter("code"));
        editableUser.setCompanyName(request.getParameter("company_name"));
        editableUser.setFirstName(request.getParameter("first_name"));
        editableUser.setLastName(request.getParameter("last_name"));
        editableUser.setPatronymic(request.getParameter("patronymic"));
        editableUser.setPhone(request.getParameter("phone"));
    }

    public static List<User> getUserListDependOnFilter(HttpServletRequest request) throws DBException {
        UserDAO userManager = DBManager.getInstance().getUserDAO();
        List<User> users;
        String userFilter = request.getParameter("userFilter");
        request.setAttribute("userFilter", userFilter);
        if (userFilter == null || userFilter.length() == 0) {
            users = userManager.getAllUsers();
        } else {
            users = userManager.getUsersByFilter(userFilter);
        }
        return users;
    }

    public static String getValue(String value) {
        if (value != null) return value;
        else return "";
    }

    public static String createCompanyName(User user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getValue(user.getLastName()));
        stringBuilder.append(" ");
        stringBuilder.append(getValue(user.getFirstName()));
        stringBuilder.append(" ");
        stringBuilder.append(getValue(user.getPatronymic()));
        return stringBuilder.toString();
    }
}
