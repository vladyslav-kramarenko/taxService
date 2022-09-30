package com.my.kramarenko.taxService.web.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Login command.
 *
 * @author Vlad Kramarenko
 */
public class InfoCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(InfoCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        LOG.trace("Command starts: info");

        String forward = Path.PAGE_INFO;

        LOG.trace("Command finished: info");
        return forward;
    }
}