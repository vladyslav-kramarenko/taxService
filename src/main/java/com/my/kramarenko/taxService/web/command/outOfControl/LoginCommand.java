package com.my.kramarenko.taxService.web.command.outOfControl;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.PasswordCreator;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.db.mySQL.UserManager;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.command.LastPage;
import com.my.kramarenko.taxService.web.Path;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

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
                          HttpServletResponse response) throws IOException, ServletException, DBException {

        LOG.debug("Command starts");
        String errorMessage;
        String forward = Path.PAGE_ERROR_PAGE;
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String lastPage = LastPage.getPage((String) request.getSession().getAttribute("page"));
        if (lastPage.isEmpty()) {
            LOG.trace("Last psage is empty => set login page as last page");
            request.getSession().setAttribute("page", Path.PAGE_LOGIN);
        }
        if (email == null || email.isEmpty()) {
//            error("Email is emty", request);
            return Path.PAGE_LOGIN;
        } else if (password == null || password.isEmpty()) {
            error("Pasword is emty", request);
            return Path.PAGE_LOGIN;
        } else {
            Optional<User> user = findUser(email, password);
            if (user.isEmpty()) {
                error("Cannot find user with such login/password", request);
                return forward;
            } else {
                setUserInSession(user.get(), request);
                forward = LastPage.getPage((String) request.getSession().getAttribute("page"));
                LOG.debug("Last page = " + forward);
                if (forward.isEmpty() || forward.equals(Path.PAGE_LOGIN)) {
                    forward = Path.COMMAND_SETTINGS;
                }
                response.sendRedirect(forward);
                return null;
            }
        }
    }

    private void error(String errorMessage, HttpServletRequest request) {
        request.setAttribute("errorMessage", errorMessage);
        LOG.error("errorMessage --> " + errorMessage);
    }

    private void setUserInSession(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        Role userRole = Role.getRole(user);
        session.setAttribute("userRole", userRole);

        LOG.info("User " + user.getEmail() + " logged as " + userRole.toString().toLowerCase());
    }

    private Optional<User> findUser(String email, String password) throws DBException {
        Optional<User> user = new UserManager().findUserByEmail(email);
        if (user.isPresent()) {
            LOG.trace(user.get());
            String userPassword = user.get().getPassword();
            String generatedPassword = PasswordCreator.getPassword(password);
            if (generatedPassword.equals(userPassword)) {
                return user;
            }else{
                LOG.debug("password is incorrect");
            }
        }
        return Optional.empty();
    }
}