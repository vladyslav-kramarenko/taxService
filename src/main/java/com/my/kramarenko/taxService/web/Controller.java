package com.my.kramarenko.taxService.web;

import com.my.kramarenko.taxService.db.enums.SortType;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.command.CommandContainer;
import jakarta.servlet.annotation.MultipartConfig;
import org.apache.log4j.Logger;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * Main servlet controller.
 *
 * @author Vlad Kramarenko
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024,    // 1 MB
        maxFileSize = 1024 * 1024 * 5,        // 5 MB
        maxRequestSize = 1024 * 1024 * 10)    // 10 MB
public class Controller extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 2423353715955164816L;
    private static final Logger LOG = Logger.getLogger(Controller.class);

    /**
     * Init servlet config
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        List<SortType> sortTypes = new ArrayList<>();
        sortTypes.add(SortType.TYPE);
        sortTypes.add(SortType.STATUS);
        sortTypes.add(SortType.DATE);
        getServletContext().setAttribute("sortTypes", sortTypes);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        process(request, response);

    }

    /**
     * Main method of this controller.
     */
    private void process(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        LOG.trace("Controller starts");
        String method = request.getMethod();
        LOG.trace("Method == " + method);
        String address;
        RequestDispatcher dispatcher;
        try {
            String commandName = request.getParameter("command");
                Command command = CommandContainer.getCommand(commandName);
                address = command.execute(request, response);
                if (address != null) {
                    if (method.equals("GET")) {
                        dispatcher = request.getRequestDispatcher(address);
                        LOG.trace("Forward to: " + address);
                        dispatcher.forward(request, response);
                    } else {
                        LOG.trace("Redirect to: " + address);
                        response.sendRedirect(address);
                    }
                } else {
                    LOG.trace("forward address is null");
                }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
}