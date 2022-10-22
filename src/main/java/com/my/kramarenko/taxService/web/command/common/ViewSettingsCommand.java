package com.my.kramarenko.taxService.web.command.common;

import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.exception.CommandException;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.db.PasswordCreator;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.dao.UserDAO;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.util.UserUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serial;

import static com.my.kramarenko.taxService.Util.resetAvailableError;

/**
 * View settings command.
 *
 * @author Vlad Kramarenko
 */
public class ViewSettingsCommand extends Command {

    @Serial
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(ViewSettingsCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException, CommandException {
        LOG.trace("Command starts");

        if (request.getMethod().equals("POST")) {
            String save = request.getParameter("save");
            LOG.trace("save = " + save);
            if (save.equals("true")) {
                return setNewUserSettings(request);
            }
        }

        resetAvailableError(request);

        request.getSession().setAttribute("page", Path.PAGE_SETTINGS);
        LOG.trace("Command finished");
        return Path.PAGE_SETTINGS;
    }

    private String setNewUserSettings(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User userInSession = (User) session.getAttribute("user");
        LOG.trace("user in session: " + userInSession);
        String password = request.getParameter("password");
        String currentPassword = request
                .getParameter("currentPassword");

        if (PasswordCreator.getPassword(currentPassword).equals(userInSession.getPassword())) {
            try {
                LOG.trace("current password == user password");
                UserUtil.setUserFields(userInSession, request);
                if (password != null && password.length() > 0) {
                    userInSession.setPassword(PasswordCreator.getPassword(password));
                } else {
                    userInSession.setPassword(userInSession.getPassword());
                }
                UserDAO userManager = DBManager.getInstance().getUserDAO();
                userManager.updateUser(userInSession);
                session.setAttribute("user", userInSession);
            } catch (DBException e) {
                LOG.error(e.getMessage());
                throw new CommandException(e.getMessage(), e);
            }
        } else {
            return Path.COMMAND_SETTINGS + "&error=Wrong current password";
        }
        return Path.COMMAND_SETTINGS;
    }
}