package com.my.kramarenko.taxService.web.filter;

import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.web.Path;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

/**
 * Security filter
 *
 * @author Vlad Kramarenko
 */
public class CommandAccessFilter implements Filter {

    private static final Logger LOG = Logger
            .getLogger(CommandAccessFilter.class);

    // commands access
    private static final Map<Role, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = null;
    private static List<String> outOfControl = null;

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        LOG.trace("Filter starts");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (isAccessAllowed(request)) {
            LOG.trace(request.getParameter("command") + " => access allowed");
            LOG.trace("Filter finished");
            chain.doFilter(request, response);
        } else {
            String errorMessage = "You do not have permission to access the requested resource";
            LOG.error("Set the request attribute: errorMessage --> "
                    + errorMessage);
            response.sendRedirect(Path.COMMAND_LOGIN + "&error=" + errorMessage);
        }
    }

    private boolean isAccessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");
        commandName = commandName.trim();
        if (commandName.isEmpty()) {
            LOG.trace("CommandName is empty");
            return false;
        }

        if (outOfControl.contains(commandName)) {
            LOG.trace("Command is out of control");
            return true;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            LOG.trace("session == null");
            return false;
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {
            LOG.info("User == null");
            return false;
        }

        if (user.isBanned()) {
            LOG.info("User is banned");
            return false;
        }

        Role userRole = Role.getRole(user);

        LOG.trace("userRole == " + userRole);
        LOG.trace("accessMap of " + userRole + " contains " + commandName + ": " + accessMap.get(userRole).contains(commandName));
        LOG.trace("commons contains " + commandName + ": " + commons.contains(commandName));


        return accessMap.get(userRole).contains(commandName)
                || commons.contains(commandName);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        LOG.trace("Filter initialization starts");

        // roles
        accessMap.put(Role.INSPECTOR, asList(fConfig.getInitParameter("inspector")));
        accessMap.put(Role.USER, asList(fConfig.getInitParameter("user")));
        accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("admin")));
        LOG.debug("Access map --> " + accessMap);

        // commons
        setCommons(asList(fConfig.getInitParameter("common")));
        LOG.debug("Common commands --> " + commons);

        // out of control
        setOutOfControl(asList(fConfig.getInitParameter("out-of-control")));
        LOG.debug("Out of control commands --> " + outOfControl);

        LOG.trace("Filter initialization finished");
    }

    /**
     * @param commons the commons to set
     */
    public static void setCommons(List<String> commons) {
        CommandAccessFilter.commons = commons;
    }

    /**
     * @param outOfControl the outOfControl to set
     */
    public static void setOutOfControl(List<String> outOfControl) {
        CommandAccessFilter.outOfControl = outOfControl;
    }

    /**
     * Extracts parameter values from string.
     *
     * @param str parameter values string.
     * @return list of parameter values.
     */
    private static List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }

}