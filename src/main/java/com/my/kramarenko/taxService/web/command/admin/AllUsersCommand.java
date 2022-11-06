package com.my.kramarenko.taxService.web.command.admin;

import com.my.kramarenko.taxService.web.Util;
import com.my.kramarenko.taxService.db.dao.UserDAO;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.Command;
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

        List<User> users = Util.getUserListDependOnFilterInRequest(request);
        LOG.info("Found in DB " + users.size() + " users");

        Util.setReportsWithPagination(users, request);

        LOG.debug("Commands finished");
        session.setAttribute("page", Path.COMMAND_ALL_USERS);
        return Path.PAGE_ALL_USERS;
    }

    /**
     * Update role for user in request
     * (uses request parameters "user_id" and "role_id")
     *
     * @param request Servlet request
     * @throws DBException
     */
    private static void updateUserRole(HttpServletRequest request) throws DBException {
        UserDAO userManager = DBManager.getInstance().getUserDAO();
        int userId = Integer.parseInt(request.getParameter("user_id"));
        int userRoleId = Integer.parseInt(request.getParameter("role_id"));
        LOG.trace("changeUserRole to " + Role.getRole(userRoleId));
        userManager.updateUserRole(userId, userRoleId);
    }

    /**
     * Update banned status (request parameter "banned_status")
     * for user (request parameter "user_id")
     *
     * @param request Servlet request
     * @throws DBException
     */
    private static void setBanned(HttpServletRequest request) throws DBException {
        UserDAO userManager = DBManager.getInstance().getUserDAO();
        int userId = Integer.parseInt(request.getParameter("user_id"));
        String banned = request.getParameter("banned_status");
        LOG.trace("changeUserBannedStatus to " + banned);
        userManager.setBanned(userId, Boolean.parseBoolean(banned));
    }
}