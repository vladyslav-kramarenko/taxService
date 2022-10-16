package com.my.kramarenko.taxService.web;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.XmlException;
import com.my.kramarenko.taxService.db.enums.SortType;
import com.my.kramarenko.taxService.db.entity.Status;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Main servlet controller.
 *
 * @author Vlad Kramarenko
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1,    // 1 MB
        maxFileSize = 1024 * 1024 * 5,        // 5 MB
        maxRequestSize = 1024 * 1024 * 10)    // 10 MB
public class Controller extends HttpServlet {

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
                         HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        process(request, response);

    }

    /**
     * Main method of this controller.
     */
    private void process(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {

        LOG.debug("Controller starts");
        String method = request.getMethod();
        LOG.trace("Method == " + method);
        String address = Path.PAGE_ERROR_PAGE;
        RequestDispatcher dispatcher;
        try {
            // extract command name from the request
            String commandName = request.getParameter("command");
            LOG.trace("Request parameter: command --> [" + commandName + "]");

            // obtain command object by its name
            Command command = CommandContainer.getCommand(commandName);
            LOG.trace("Obtained command --> [" + command + "]");

            // execute command and get forward address
            address = command.execute(request, response);
            LOG.trace("Forward address --> " + address);

        } catch (DBException e) {
            LOG.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
//            response.sendRedirect(Path.PAGE_ERROR_PAGE);
        } catch (XmlException e) {
//            LOG.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            request.setAttribute("errorMessage", e.getMessage());
//            response.sendRedirect(Path.PAGE_ERROR_PAGE);
        } catch (Exception | Error e) {
//            LOG.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            request.setAttribute("errorMessage", e.getMessage());
        } finally {
            LOG.trace("Controller finished");
            try {
                if (address != null) {
                    LOG.trace("go to forward address --> " + address);
                    dispatcher = request.getRequestDispatcher(address);
                    dispatcher.forward(request, response);
                }
            } catch (Exception | Error e) {
                LOG.error(e.getMessage());
                request.setAttribute("errorMessage", e.getMessage());
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
                dispatcher = request.getRequestDispatcher(Path.PAGE_ERROR_PAGE);
                dispatcher.forward(request, response);
            }
        }
    }
}