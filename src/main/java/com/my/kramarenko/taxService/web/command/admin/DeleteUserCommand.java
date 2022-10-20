package com.my.kramarenko.taxService.web.command.admin;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.PasswordCreator;
import com.my.kramarenko.taxService.db.dao.UserDao;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.command.util.UserUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * View settings command.
 *
 * @author Vlad Kramarenko
 */
public class DeleteUserCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(DeleteUserCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException {
        LOG.debug("Command starts");
        UserDao userManager = DBManager.getInstance().getUserManager();
        int userId = Integer.parseInt(request.getParameter("userId"));
        userManager.deleteUser(userId);
        LOG.debug("Command finished");
        return Path.COMMAND_ALL_USERS;
    }
}