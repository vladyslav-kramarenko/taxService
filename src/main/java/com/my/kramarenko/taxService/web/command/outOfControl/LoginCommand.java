package com.my.kramarenko.taxService.web.command.outOfControl;

import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.db.PasswordCreator;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.command.LastPage;
import com.my.kramarenko.taxService.web.Path;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.Serial;
import java.util.Map;
import java.util.Optional;

import static com.my.kramarenko.taxService.Util.resetAvailableError;

/**
 * Login command.
 *
 * @author Vlad Kramarenko
 */
public class LoginCommand extends Command {

    @Serial
    private static final long serialVersionUID = -3071536593627692473L;
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) {

        LOG.debug("Command starts");

        String lastPage = LastPage.getPage((String) request.getSession().getAttribute("page"));

        if (lastPage.isEmpty()) {
            LOG.trace("Last psage is empty => set login page as last page");
            lastPage = Path.PAGE_LOGIN;
            request.getSession().setAttribute("page", lastPage);
        }

        if (request.getMethod().equals("GET")) {
            resetAvailableError(request);
            return Path.PAGE_LOGIN;
        }
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String forward;
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            String message = "password or email is empty";
            return Path.COMMAND_LOGIN + "&error=" + message;
        } else {
            Optional<User> user = findUser(email, password);
            if (user.isEmpty()) {
                String message = "Cannot find user with such login/password";
                LOG.trace(message);
                request.setAttribute("error", message);
                return Path.COMMAND_LOGIN + "&error=" + message;
            } else {
                setUserInSession(user.get(), request);
                forward = LastPage.getPage((String) request.getSession().getAttribute("page"));
                LOG.debug("Last page = " + forward);
                if (forward.isEmpty() || forward.equals(Path.PAGE_LOGIN)) {
                    forward = Path.COMMAND_SETTINGS;
                }
                return forward;
            }
        }
    }

//    private void error(String errorMessage, HttpServletRequest request) {
//        request.setAttribute("errorMessage", errorMessage);
//        LOG.error("errorMessage --> " + errorMessage);
//    }

    private void setUserInSession(User user, HttpServletRequest request) {
        ServletContext sc = request.getServletContext();
        Map<Integer, Role> roleMap = (Map<Integer, Role>) sc.getAttribute("roleMap");
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        Role userRole = roleMap.get(user.getRoleId());
        session.setAttribute("userRole", userRole);

        LOG.info("User " + user.getEmail() + " logged as " + userRole.toString().toLowerCase());
    }

    private Optional<User> findUser(String email, String password) {
        try {
            Optional<User> user = DBManager.getInstance().getUserDAO().findUserByEmail(email);
            if (user.isPresent()) {
                LOG.trace(user.get());
                String userPassword = user.get().getPassword();
                String generatedPassword = PasswordCreator.getPassword(password);
                if (generatedPassword.equals(userPassword)) {
                    return user;
                } else {
                    LOG.debug("password is incorrect");
                }
            }
        } catch (DBException e) {
            LOG.error(e.getMessage(),e);
        }
        return Optional.empty();
    }
}