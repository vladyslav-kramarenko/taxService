package com.my.kramarenko.taxService.web.listener;

import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.db.entity.Type;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Context listener.
 * <p>
 * * @author Vlad Kramarenko
 */
public class ContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(ContextListener.class);

    public void contextDestroyed(ServletContextEvent event) {
        log("Servlet context destruction starts");
        // do nothing
        log("Servlet context destruction finished");
    }

    public void contextInitialized(ServletContextEvent event) {
        log("Servlet context initialization starts");

        ServletContext servletContext = event.getServletContext();
        initLog4J(servletContext);
        initCommandContainer();
        initI18N(servletContext);
        initStatuses(servletContext);
        initTypes(servletContext);
        initRoles(servletContext);

        log("Servlet context initialization finished");
    }

    /**
     * Initializes i18n subsystem.
     */
    private void initI18N(ServletContext servletContext) {
        LOG.trace("I18N subsystem initialization started");

        String localesValue = servletContext.getInitParameter("locales");
        if (localesValue == null || localesValue.isEmpty()) {
            LOG.warn("'locales' init parameter is empty, the default encoding will be used");
        } else {
            List<String> locales = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(localesValue);
            while (st.hasMoreTokens()) {
                String localeName = st.nextToken();
                locales.add(localeName);
            }

            LOG.debug("Application attribute set: locales --> " + locales);
            servletContext.setAttribute("locales", locales);
        }

        LOG.trace("I18N subsystem initialization finished");
    }

    /**
     * Initializes list of available statuses
     *
     * @param servletContext Servlet Context
     */
    private void initStatuses(ServletContext servletContext) {
        LOG.trace("Status list initialization started");
        Map<Integer, Status> statusMap;
        Status[] statuses = Status.values();
        statusMap = Arrays.stream(statuses)
                .collect(Collectors.toMap(Status::getId, x -> x));
        LOG.debug("Statuses set:\n" + statusMap.values());

//        servletContext.setAttribute("allStatuses", statuses);
        servletContext.setAttribute("statusMap", statusMap);
        LOG.trace("Status list initialization finished");
    }

    /**
     * Initializes list of available report types
     *
     * @param servletContext Servlet Context
     */
    private void initTypes(ServletContext servletContext) {
        LOG.trace("Types list initialization started");
        Map<String, Type> typeMap;
        try {
            List<Type> types = DBManager.getInstance().getTypeDAO().getAllTypes();
            typeMap = types
                    .stream()
                    .collect(Collectors.toMap(Type::getId, x -> x));
            LOG.debug("Types set:\n" + typeMap.values());
        } catch (DBException e) {
            LOG.error("Cannot obtain types list");
            throw new RuntimeException(e);
        }
        servletContext.setAttribute("typeMap", typeMap);
        LOG.trace("Types list initialization finished");
    }

    /**
     * Initializes list of available report types
     *
     * @param servletContext Servlet Context
     */
    private void initRoles(ServletContext servletContext) {
        LOG.trace("Roles initialization started");
        Map<Integer, Role> roleMap = Arrays.stream(Role.values()).collect(Collectors.toMap(Role::getId, x -> x));
        servletContext.setAttribute("roleMap", roleMap);
        LOG.trace("Roles initialization finished");
    }

    /**
     * Initializes log4j framework.
     *
     * @param servletContext Servlet Context
     */
    private void initLog4J(ServletContext servletContext) {
        log("Log4J initialization started");
        try {
            PropertyConfigurator.configure(servletContext
                    .getRealPath("WEB-INF/log4j.properties"));
        } catch (Exception ex) {
            LOG.error(ex.getMessage(),ex);
        }

        log("Log4J initialization finished");
    }

    /**
     * Initializes CommandContainer.
     * <p>
     * //	 * @param servletContext
     */
    private void initCommandContainer() {
        LOG.trace("Command container initialization started");

        // initialize commands container
        // just load class to JVM
        try {
            Class.forName("com.my.kramarenko.taxService.web.command.CommandContainer");
        } catch (ClassNotFoundException ex) {
            LOG.error(ex.getMessage(),ex);
        }
        LOG.trace("Command container initialization finished");
    }

    private void log(String msg) {
        System.out.println("[ContextListener] " + msg);
    }

}