package com.my.kramarenko.taxService.web.command.common;

import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.Path;
import jakarta.servlet.jsp.jstl.core.Config;
import org.apache.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class UpdateLocaleCommand extends Command {

    private static final long serialVersionUID = 7732286214029478505L;

    private static final Logger LOG = Logger
            .getLogger(UpdateLocaleCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        LOG.trace("Command starts");

        String localeToSet = request.getParameter("localeToSet");
        LOG.trace("Locale to set: " + localeToSet);
        if (localeToSet != null && !localeToSet.isEmpty()) {
            HttpSession session = request.getSession();
            Config.set(session, "jakarta.servlet.jsp.jstl.fmt.locale",
                    localeToSet);
            session.setAttribute("defaultLocale", localeToSet);
        }

        LOG.trace("Command finished");
        HttpSession session = request.getSession();
        String lastPage = (String) session.getAttribute("page");
        LOG.debug("last page: " + lastPage);
        if (lastPage != null) {
            return lastPage;
        }

        return Path.PAGE_INFO;
    }

}