package com.my.kramarenko.taxService.web.command.admin;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.PasswordCreator;
import com.my.kramarenko.taxService.db.dao.UserDao;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.command.util.UserUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * View settings command.
 *
 * @author Vlad Kramarenko
 */
public class EditUserCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(EditUserCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException {
        LOG.debug("Command starts");

        if (request.getMethod().equals("POST")) {
            String save = request.getParameter("save");
            LOG.trace("save = " + save);
            if (save.equals("true")) {
                setNewUserSettings(request);
                response.sendRedirect(Path.COMMAND_SETTINGS);
//                if (setNewUserSettings(request)) {
//                    response.sendRedirect(Path.COMMAND_SETTINGS);
//                    return null;
//                }
            }
        }
        request.getSession().setAttribute("page", Path.PAGE_SETTINGS);
        LOG.debug("Command finished");
        return Path.PAGE_SETTINGS;
    }

    private void setNewUserSettings(HttpServletRequest request) throws DBException {
        try {
            UserDao userManager = DBManager.getInstance().getUserManager();
            int userId = Integer.parseInt(request.getParameter("userId"));
            User editableUser = userManager.getUser(userId);
            UserUtil.setUserFields(editableUser, request);
            String password = request.getParameter("password");
            if (password != null && password.length() > 0) {
                editableUser.setPassword(PasswordCreator.getPassword(password));
            }
            userManager.updateUser(editableUser);
        } catch (DBException e) {
            LOG.error(e.getMessage());
            request.setAttribute("error", e.getMessage());
            throw new DBException("Can't update the user", e);
        }
    }
}