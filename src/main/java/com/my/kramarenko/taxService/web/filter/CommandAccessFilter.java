package com.my.kramarenko.taxService.web.filter;

import com.my.taxservice.db.enums.Role;
import com.my.taxservice.web.command.Path;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
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
    private static Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();
    private static List<String> commons = null;
    private static List<String> outOfControl = null;

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        LOG.debug("Filter starts");

        if (accessAllowed(request)) {
            LOG.debug("Filter finished");
            chain.doFilter(request, response);
        } else {
            String errorMessasge = "You do not have permission to access the requested resource";

            request.setAttribute("errorMessage", errorMessasge);
            LOG.trace("Set the request attribute: errorMessage --> "
                    + errorMessasge);

            request.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(request,
                    response);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");
        if (commandName == null || commandName.isEmpty()) {
            return false;
        }

        if (outOfControl.contains(commandName)) {
            return true;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }

        Role userRole = (Role) session.getAttribute("userRole");
        if (userRole == null) {
            return false;
        }

        return accessMap.get(userRole).contains(commandName)
                || commons.contains(commandName);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        LOG.debug("Filter initialization starts");

        // roles
        accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("admin")));
        accessMap.put(Role.CLIENT, asList(fConfig.getInitParameter("client")));
        LOG.trace("Access map --> " + accessMap);

        // commons
        setCommons(asList(fConfig.getInitParameter("common")));
        LOG.trace("Common commands --> " + commons);

        // out of control
        setOutOfControl(asList(fConfig.getInitParameter("out-of-control")));
        LOG.trace("Out of control commands --> " + outOfControl);

        LOG.debug("Filter initialization finished");
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
        List<String> list = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }

}