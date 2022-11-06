package com.my.kramarenko.taxService.web.command.outOfControl;

import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.db.PasswordCreator;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.VerifyRecaptcha;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serial;
import java.util.Map;
import java.util.Optional;

import static com.my.kramarenko.taxService.web.Util.resetAvailableError;
import static com.my.kramarenko.taxService.web.Util.updateLastPageIfEmpty;

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
                          HttpServletResponse response) throws IOException {

        LOG.debug("Command starts");

        String lastPage = updateLastPageIfEmpty(request, Path.PAGE_LOGIN);

        if (request.getMethod().equals("GET")) {
            resetAvailableError(request);
            return Path.PAGE_LOGIN;
        }
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String forward;
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return forwardError("error.empty_credentials");
        } else {
            String gRecaptchaResponse = request
                    .getParameter("g-recaptcha-response");
            boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
            if (!verify) {
                return forwardError("error.invalid_captcha");
            }
            LOG.trace("captcha is OK");
            Optional<User> user = findUser(email, password);
            if (user.isEmpty()) {
                return forwardError("error.cannot_find_user");
            } else {
                setUserInSession(user.get(), request);
                forward = lastPage;

                if (forward.isEmpty() || forward.equals(Path.PAGE_LOGIN)) {
                    forward = Path.COMMAND_SETTINGS;
                }
                return forward;
            }
        }
    }

    private String forwardError(String errorMessage) {
        LOG.trace(errorMessage);
        String forward = Path.COMMAND_LOGIN + "&error=" + errorMessage;
        return forward;
    }

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
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }
}