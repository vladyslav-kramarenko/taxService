package com.my.kramarenko.taxService.web.command.common;

import com.my.kramarenko.taxService.db.dao.UserDAO;
import com.my.kramarenko.taxService.db.entity.UserDetails;
import com.my.kramarenko.taxService.db.DBManager;
import com.my.kramarenko.taxService.exception.CommandException;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.db.PasswordCreator;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.mySQL.dao.UserDAOMySQL;
import com.my.kramarenko.taxService.web.Util;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.Path;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serial;

import static com.my.kramarenko.taxService.web.Util.resetAvailableError;

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
                return updateUserSettings(request);
            }
        }

        try {
            resetAvailableError(request);

            User user = (User) request.getSession().getAttribute("user");
            UserDetails userDetails = DBManager.getInstance().getUserDAO().getUserDetails(user.getId());

            request.setAttribute("userDetails", userDetails);
            request.getSession().setAttribute("page", Path.PAGE_SETTINGS);
            LOG.trace("Command finished");
            return Path.PAGE_SETTINGS;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new CommandException("error.not_found", e);
        }
    }

    /**
     * Update user (session attribute "user") parameters from parameters in request
     *
     * @param request Servlet request
     * @return forward path
     * @throws CommandException
     */
    private String updateUserSettings(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User userInSession = (User) session.getAttribute("user");
        LOG.trace("user in session: " + userInSession);
        String password = request.getParameter("password");
        String currentPassword = request
                .getParameter("currentPassword");

        if (PasswordCreator.getPassword(currentPassword).equals(userInSession.getPassword())) {
            try {
                LOG.trace("current password == user password");
                Util.setUserFieldsFromRequest(userInSession, request);
                UserDetails userDetails = new UserDetails();
                userDetails.setUserId(userInSession.getId());
                Util.setUserDetailsFieldsFromRequest(userDetails, request);

                if (password != null && password.length() > 0) {
                    userInSession.setPassword(PasswordCreator.getPassword(password));
                } else {
                    userInSession.setPassword(userInSession.getPassword());
                }

                UserDAO userManager = DBManager.getInstance().getUserDAO();

                userManager.updateUser(userInSession, userDetails);

                session.setAttribute("user", userInSession);
                session.setAttribute("userDetails", userDetails);
            } catch (DBException e) {
                LOG.error(e.getMessage(), e);
                throw new CommandException(e.getMessage(), e);
            }
        } else {
            return Path.COMMAND_SETTINGS + "&error=Wrong current password";
        }
        return Path.COMMAND_SETTINGS;
    }
}