package com.my.kramarenko.taxService.web.command;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.entity.PasswordCreator;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.db.mySQL.UserDb;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Login command.
 *
 * @author Vlad Kramarenko
 */
public class LoginCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        String forward = Path.PAGE_ERROR_PAGE;

        String email = request.getParameter("email");
        if (email != null) {
            LOG.trace("Request parameter: login --> " + email);
            String password = request.getParameter("password");

            if (password == null || email.isEmpty() || password.isEmpty()) {
                error(request, "Email/password cannot be empty");
                return forward;
            }

            UserDb userDb = new UserDb();
            User user = userDb.findUserByEmail(email);
            if (user == null || !PasswordCreator.getPassword(email, password).equals(user.getPassword())) {
                LOG.debug("user==null");
                String errorMessage = "Cannot find a user with such login/password";
                try {
                    Locale locale = request.getLocale();
                    LOG.trace("user locale: " + locale);
                    ResourceBundle words
                            = ResourceBundle.getBundle("resources", locale);
                    errorMessage = words.getString("cannotFindUser");
                } catch (Exception e) {
                    LOG.error("localization error:" + e.getMessage());
                }
                LOG.debug("error message = " + errorMessage);
                error(request, errorMessage);
                return forward;
            } else {
                Role userRole = Role.getRole(user);
                LOG.trace("userRole --> " + userRole);

                session.setAttribute("user", user);
                LOG.trace("Set the session attribute: user --> " + user);

                session.setAttribute("userRole", userRole);
                LOG.trace("Set the session attribute: userRole --> " + userRole);

                LOG.info("User " + user + " logged as "
                        + userRole.toString().toLowerCase());

                String lastPage = LastPage.getPage((String) session
                        .getAttribute("page"));
                LOG.trace("LoginCommand LastPage: " + lastPage);

                if (lastPage.isEmpty()) {
                    lastPage = Path.DEFAULT_PAGE;
                }
                response.sendRedirect(lastPage);
            }
        } else {
            forward = Path.PAGE_LOGIN;
        }

        LOG.trace("Command finished");
        return forward;
    }

    private void error(HttpServletRequest request, String errorMessage) {
        request.setAttribute("errorMessage", errorMessage);
        LOG.error("errorMessage --> " + errorMessage);
    }
}