package com.my.kramarenko.taxService.web.command.admin;

import com.my.kramarenko.taxService.Util;
import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.dao.UserDao;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.command.util.UserUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serial;
import java.util.List;

public class AllUsersCommand extends Command {

    @Serial
    private static final long serialVersionUID = 1863978254689586513L;

    private static final Logger LOG = Logger.getLogger(AllUsersCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException {
        LOG.debug("Commands starts");

        HttpSession session = request.getSession();

        if (request.getParameter("command").equals("changeUserRole")) {
            updateUserRole(request);
        }

        if (request.getParameter("command").equals("changeUserBannedStatus")) {
            setBanned(request);
        }

        List<User> users = UserUtil.getUserListDependOnFilter(request);
        LOG.info("Found in DB " + users.size() + " users");

        Util.setReportsWithPagination(users, request);

        LOG.debug("Commands finished");
        session.setAttribute("page", Path.PAGE_ALL_USERS);
        return Path.PAGE_ALL_USERS;
    }

    private static void updateUserRole(HttpServletRequest request) throws DBException {
        UserDao userManager = DBManager.getInstance().getUserManager();
        int userId = Integer.parseInt(request.getParameter("user_id"));
        int userRoleId = Integer.parseInt(request.getParameter("role_id"));
        LOG.trace("changeUserRole to " + Role.getRole(userRoleId));
        userManager.updateUserRole(userId, userRoleId);
    }

    private static void setBanned(HttpServletRequest request) throws DBException {
        UserDao userManager = DBManager.getInstance().getUserManager();
        int userId = Integer.parseInt(request.getParameter("user_id"));
        String banned = request.getParameter("banned_status");
        LOG.trace("changeUserBannedStatus to " + banned);
        userManager.setBanned(userId, Boolean.parseBoolean(banned));
    }
}