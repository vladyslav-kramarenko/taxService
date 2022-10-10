package com.my.kramarenko.taxService.web.command.common;

import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.Path;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serial;

/**
 * Logout command.
 * 
 * @author Vlad Kramarenko
 * 
 */
public class LogoutCommand extends Command {

	@Serial
	private static final long serialVersionUID = -2785976616686657267L;

	private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

	@Override
	public String execute(HttpServletRequest request,
						  HttpServletResponse response) throws IOException, ServletException {
		LOG.trace("Command starts");

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		LOG.debug("Command finished");
		return Path.PAGE_LOGIN;
	}

}