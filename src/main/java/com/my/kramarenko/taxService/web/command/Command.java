package com.my.kramarenko.taxService.web.command;

import com.my.kramarenko.taxService.exception.CommandException;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.exception.XmlException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

/**
 * Main interface for the Command pattern implementation.
 * 
 * @author Vlad Kramarenko
 * 
 */
public abstract class Command implements Serializable {
	@Serial
	private static final long serialVersionUID = 8879403039606311780L;

	/**
	 * Execution method for command.
	 * 
	 * @return Address to go once the command is executed.
	 */
	public abstract String execute(HttpServletRequest request,
								   HttpServletResponse response) throws IOException, ServletException, DBException, XmlException, CommandException;

	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}
}