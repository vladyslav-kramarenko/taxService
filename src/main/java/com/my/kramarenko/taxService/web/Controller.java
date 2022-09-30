package com.my.kramarenko.taxService.web;

import com.my.taxservice.db.enums.SortType;
import com.my.taxservice.web.command.Command;
import com.my.taxservice.web.command.CommandContainer;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Controller", value = "/Controller")
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
        sortTypes.add(SortType.PRICE);
        sortTypes.add(SortType.PRICE_DESC);
        getServletContext().setAttribute("sortTypes", sortTypes);

        LOG.debug("SortTypes : " + sortTypes);

//        ProductDb productDb = new ProductDb();
//        double maxItemsPrice = productDb.getMaxPrice() + 1;

//        getServletContext().setAttribute("maxPrice", maxItemsPrice);
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
        // extract command name from the request
        String commandName = request.getParameter("command");
        LOG.trace("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
        LOG.trace("Obtained command --> " + command);

        // execute command and get forward address
        String forward = command.execute(request, response);
        LOG.trace("Forward address --> " + forward);

        LOG.debug("Controller finished, now go to forward address --> "
                + forward);

        // if the forward address is not null go to the address
        if (forward != null) {
            RequestDispatcher disp = request.getRequestDispatcher(forward);
            disp.forward(request, response);
        }
    }

}
