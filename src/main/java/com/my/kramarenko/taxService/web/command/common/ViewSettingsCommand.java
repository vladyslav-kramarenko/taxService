package com.my.kramarenko.taxService.web.command.user;

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

    private static final Logger LOG = Logger
            .getLogger(ViewSettingsCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException {
        LOG.debug("Command starts");

        if (request.getMethod().equals("post")) {
            String save = request.getParameter("save");
            if (save.equals("true")) {
                setNewUserSettings(request);
            }
        }
        LOG.debug("Command finished");
        return Path.PAGE_SETTINGS;
    }

    private void setNewUserSettings(HttpServletRequest request) throws DBException {
        HttpSession session = request.getSession();
        User userInSession = (User) session.getAttribute("user");

        String password = request.getParameter("password");
        String currentPassword = request
                .getParameter("currentPassword");

//        request.setAttribute("user", user);
        if (PasswordCreator.getPassword(currentPassword).equals(userInSession.getPassword())) {
            User newUser = userInSession;
            newUser.setEmail(request.getParameter("email"));
            newUser.setFirstName(request.getParameter("first_name"));
            newUser.setLastName(request.getParameter("last_name"));
            newUser.setPhone(request.getParameter("phone"));
            if (password != null && password.length() > 0) {
                newUser.setPassword(PasswordCreator.getPassword(password));
            } else {
                newUser.setPassword(userInSession.getPassword());
            }
            UserDao userDb = new UserManager();
            userDb.updateUser(newUser);
            session.setAttribute("user", newUser);
        }
    }
}