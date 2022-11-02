package com.my.kramarenko.taxService.web.command.outOfControl;

import com.my.kramarenko.taxService.Util;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.exception.CommandException;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.exception.XmlException;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

import static com.my.kramarenko.taxService.web.mail.MailCreator.resetPassword;

public class ResetPasswordCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DBException, XmlException, CommandException {
        if (request.getMethod().equals("GET")) {
            Util.resetAvailableError(request);
            return Path.PAGE_RESET_PASSWORD;
        }
        String email = request.getParameter("email");
        Optional<User> user = DBManager.getInstance().getUserDAO().findUserByEmail(email);
        if (user.isEmpty()) {
            return Path.PAGE_RESET_PASSWORD + "&error=This user does not exist";
        }
        resetPassword(email, user.get());
        return Path.COMMAND_LOGIN + "&error=Your Password has been reset";
    }
}
