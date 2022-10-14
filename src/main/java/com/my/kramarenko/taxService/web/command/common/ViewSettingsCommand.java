package com.my.kramarenko.taxService.web.command.common;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.dao.UserDao;
import com.my.kramarenko.taxService.db.PasswordCreator;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.mySQL.UserManager;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.Path;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * View settings command.
 *
 * @author Vlad Kramarenko
 */
public class ViewSettingsCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(ViewSettingsCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException {
        LOG.debug("Command starts");

        if (request.getMethod().equals("POST")) {
            String save = request.getParameter("save");
            LOG.trace("save = " + save);
            if (save.equals("true")) {
                if (setNewUserSettings(request)) {
                    response.sendRedirect(Path.COMMAND_SETTINGS);
                    return null;
                }
            }
        }
        request.getSession().setAttribute("page", Path.PAGE_SETTINGS);
        LOG.debug("Command finished");
        return Path.PAGE_SETTINGS;
    }

    private boolean setNewUserSettings(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInSession = (User) session.getAttribute("user");
        LOG.trace("user in session: " + userInSession);
        String password = request.getParameter("password");
        String currentPassword = request
                .getParameter("currentPassword");
        LOG.debug("current pas: " + currentPassword);
        LOG.debug("user pas: " + userInSession.getPassword());
        LOG.debug("new pas: " + password);

//        request.setAttribute("user", user);
        if (PasswordCreator.getPassword(currentPassword).equals(userInSession.getPassword())) {
            try {
                LOG.trace("current password == user password");
                userInSession.setEmail(request.getParameter("email"));
                userInSession.setFirstName(request.getParameter("first_name"));
                userInSession.setLastName(request.getParameter("last_name"));
                userInSession.setPatronymic(request.getParameter("patronymic"));
                userInSession.setCodePassport(request.getParameter("code_passport"));
                userInSession.setPhone(request.getParameter("phone"));
                if (password != null && password.length() > 0) {
                    userInSession.setPassword(PasswordCreator.getPassword(password));
                } else {
                    userInSession.setPassword(userInSession.getPassword());
                }
                UserDao userManager = new UserManager();
                userManager.updateUser(userInSession);
                session.setAttribute("user", userInSession);
            } catch (DBException e) {
                LOG.error(e.getMessage());
                request.setAttribute("error", e.getMessage());
            }
        } else {
            request.setAttribute("errorMessage", "Wrong current password");
        }
        return true;
    }
}