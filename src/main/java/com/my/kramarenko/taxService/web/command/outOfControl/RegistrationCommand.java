package com.my.kramarenko.taxService.web.command.common;

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

public class RegistrationCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger
            .getLogger(RegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException {
        LOG.debug("Command starts");
        HttpSession session = request.getSession();
        String forward = Path.PAGE_ERROR_PAGE;
        String email = request.getParameter("email");

        // error handler
        String errorMessage;
        if (email == null || email.isEmpty()) {
            forward = Path.PAGE_REGISTRATION;
        } else {
            LOG.trace("Request parameter: registration --> " + email);
            User user = createUserBean(request);
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                errorMessage = "Login/password cannot be empty" + "<br>";
                request.setAttribute("errorMessage", errorMessage);
                forward = Path.PAGE_REGISTRATION;
            } else {
                UserManager userManager = new UserManager();
                if (userManager.findUserByEmail(email).isPresent()) {
                    error("Such email is forbidden", request);
                } else {
                    try {
                        LOG.trace("everything is ok => write user to DB");
                        user.setPassword(PasswordCreator.getPassword(user.getPassword()));
                        userManager.addUser(user);
                        setSessionAttributes(session, user);
                        forward = LastPage.getPage((String) session
                                .getAttribute("page"));
                        if (forward.isEmpty()) {
                            forward = Path.COMMAND_SETTINGS;
                        }
                        response.sendRedirect(forward);
                        return null;
                    } catch (DBException e) {
                        error("Cannot create the user", request);
                        throw new DBException(e.getMessage(), e);
                    }
                }
            }
            setRequestAttributes(request, user);
        }
        LOG.debug("Command finished");
        return forward;
    }

    private void error(String errorMessage, HttpServletRequest request) {
        request.setAttribute("errorMessage", errorMessage);
        LOG.debug("errorMessage --> " + errorMessage);
    }

    private void setSessionAttributes(HttpSession session, User user) {
        session.setAttribute("user", user);
        LOG.trace("Set the session attribute: user --> " + user);
        Role role = Role.getRole(user);
        session.setAttribute("userRole", role);
        LOG.trace("Set the session attribute: userRole --> "
                + Role.USER);
        LOG.info("User " + user + " logged as "
                + role.toString().toLowerCase());
    }

    private void setRequestAttributes(HttpServletRequest request, User user) {
        request.setAttribute("email", user.getEmail());
        request.setAttribute("phone", user.getPhone());
        request.setAttribute("name", user.getFirstName());
        request.setAttribute("lastName", user.getLastName());
    }

    private User createUserBean(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String phone = request.getParameter("phone");
        return new User(email, password, name, surname, phone);
    }
}