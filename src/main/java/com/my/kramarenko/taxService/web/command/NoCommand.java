package com.my.kramarenko.taxService.web.command;

import com.my.kramarenko.taxService.web.Path;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * No command.
 * 
 * @author Vlad Kramarenko
 * 
 */
public class NoCommand extends Command {

	private static final long serialVersionUID = -2785976616686657267L;

	private static final Logger LOG = Logger.getLogger(NoCommand.class);

	@Override
	public String execute(HttpServletRequest request,
						  HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command starts");
		
		String errorMessage = "No such command";
		request.setAttribute("errorMessage", errorMessage);
		LOG.error("Set the request attribute: errorMessage --> " + errorMessage);

		LOG.debug("Command finished");
		return Path.PAGE_ERROR_PAGE;
	}

}