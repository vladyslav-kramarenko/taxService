package ua.nure.kramarenko.SummaryTask4.web.command.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Path;
import ua.nure.kramarenko.SummaryTask4.db.derby.UserDb;
import ua.nure.kramarenko.SummaryTask4.db.entity.User;
import ua.nure.kramarenko.SummaryTask4.db.enums.Role;
import ua.nure.kramarenko.SummaryTask4.web.command.Command;

//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AllUsersCommand extends Command {

	private static final long serialVersionUID = 1863978254689586513L;

	private static final Logger LOG = Logger.getLogger(AllUsersCommand.class);

	@Override
	public String execute(HttpServletRequest request,
						  HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Commands starts");

		HttpSession session = request.getSession();

		UserDb userDb = new UserDb();
		if (request.getParameter("command").equals("changeUserRole")) {
			int userId = Integer.parseInt(request.getParameter("user_id"));
			int userRoleId = Integer.parseInt(request.getParameter("role_id"));
			userDb.updateUserRole(userId, userRoleId);
		}

		List<User> users = userDb.getAllUsers();

		LOG.trace("Found in DB: userOrderBeanList --> " + users);

		request.setAttribute("userList", users);
		request.setAttribute("roleTypes", Role.values());
		session.setAttribute("page", "users");
		LOG.trace("Set the request attribute: userOrderBeanList --> " + users);

		LOG.debug("Commands finished");
		return Path.PAGE_ALL_USERS;
	}

}