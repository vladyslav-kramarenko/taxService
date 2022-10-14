package com.my.kramarenko.taxService.web.command.admin;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.dao.UserDao;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.db.mySQL.UserManager;
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

        UserDao userManager = new UserManager();
        if (request.getParameter("command").equals("changeUserRole")) {

            int userId = Integer.parseInt(request.getParameter("user_id"));
            int userRoleId = Integer.parseInt(request.getParameter("role_id"));
            userManager.updateUserRole(userId, userRoleId);

        }

        List<User> users = userManager.getAllUsers();

        LOG.trace("Found in DB: userOrderBeanList --> " + users);

        request.setAttribute("userList", users);
//        request.setAttribute("roleTypes", Role.values());
        session.setAttribute("page", "users");

        LOG.trace("Set the request attribute: userOrderBeanList --> " + users);

        LOG.debug("Commands finished");
        session.setAttribute("page", Path.PAGE_ALL_USERS);
        return Path.PAGE_ALL_USERS;
    }
}