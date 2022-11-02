package com.my.kramarenko.taxService.web.command.admin;

import com.my.kramarenko.taxService.db.dao.UserDAO;
import com.my.kramarenko.taxService.exception.CommandException;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.command.util.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.Serial;
import java.util.Objects;
import java.util.Optional;

import static com.my.kramarenko.taxService.Util.resetAvailableError;

/**
 * View settings command.
 *
 * @author Vlad Kramarenko
 */
public class EditUserCommand extends Command {

    @Serial
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(EditUserCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        LOG.trace("Command starts");
        try {
            if (request.getMethod().equals("POST")) {
                String save = request.getParameter("save");
                LOG.trace("save = " + save);
                if (Objects.equals(save, "true")) {
                    return setNewUserSettings(request);
                }
            } else {
                resetAvailableError(request);
            }
            int userId = Integer.parseInt(request.getParameter("userId"));
            Optional<User> user = DBManager.getInstance().getUserDAO().getUser(userId);
            request.setAttribute("userEdit", user.get());
            request.getSession().setAttribute("page", Path.COMMAND_ALL_USERS);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            throw new CommandException("Can't show user's info page", e);
        }
        LOG.trace("Command finished");
        return Path.PAGE_EDIT_USER;
    }

    private String setNewUserSettings(HttpServletRequest request) throws CommandException {
        int userId = -1;
        try {
            UserDAO userManager = DBManager.getInstance().getUserDAO();
            userId = Integer.parseInt(request.getParameter("userId"));
            User editableUser = userManager.getUser(userId).orElseThrow();
            UserUtil.setUserFields(editableUser, request);
//            String password = request.getParameter("password");
//            if (password != null && password.length() > 0) {
//                editableUser.setPassword(PasswordCreator.getPassword(password));
//            }
            userManager.updateUser(editableUser);
            return Path.COMMAND_EDIT_USER + "&userId=" + userId+ "&error=User info is updated";
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            if (userId != -1) {
                return Path.COMMAND_EDIT_USER + "&userId=" + userId + "&error=Can't update user's info";
            }
            else {
                throw new CommandException("Can't update user's info", e);
            }
        }
    }
}