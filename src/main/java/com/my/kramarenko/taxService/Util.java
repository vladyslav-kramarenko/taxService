package com.my.kramarenko.taxService;

import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.LastPage;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import java.util.List;

public class Util {
    private static final Logger LOG = Logger.getLogger(Util.class);

    public static int getIntValue(int defaultValue, String value) {
        if (value != null) {
            try {
                defaultValue = Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return defaultValue;
    }

    public static void setReportsWithPagination(List<?> reportsList, HttpServletRequest request) {
        int recordsPerPage = Util.getIntValue(10, request.getParameter("recordsPerPage"));
        int selectedPage = Util.getIntValue(1, request.getParameter("selectedPage"));

        LOG.trace("selected page = " + selectedPage);
        int maxReport = selectedPage * recordsPerPage;
        if (reportsList.size() < selectedPage * recordsPerPage) maxReport = reportsList.size();
        int minReport = (selectedPage - 1) * recordsPerPage;
        LOG.trace("show users from " + minReport + " to " + maxReport);
        int noOfPages = (int) Math.ceil((double) reportsList.size() / recordsPerPage);
        LOG.trace("noOfPages = " + noOfPages);
        List<?> chosenReports = reportsList.subList(minReport, maxReport);
        LOG.trace(chosenReports);
        request.setAttribute("paginationList", chosenReports);

        request.setAttribute("noOfPages", noOfPages);

        request.setAttribute("recordsPerPage", recordsPerPage);
        LOG.trace("set recordsPerPage=" + recordsPerPage);
        request.setAttribute("selectedPage", selectedPage);
        LOG.trace("set selectedPage=" + selectedPage);
    }

    public static void resetAvailableError(HttpServletRequest request) {
        String error = request.getParameter("error");
        if (error != null && error.length() > 0)
            request.setAttribute("errorMessage", error);
    }

    public static String updateLastPageIfEmpty(HttpServletRequest request, String currentPage) {
        String lastPage = LastPage.getPage((String) request.getSession().getAttribute("page"));
        if (lastPage.isEmpty()) {
            LOG.trace("Last psage is empty => set login page as last page");
            lastPage = currentPage;
            request.getSession().setAttribute("page", lastPage);
        }
        return lastPage;
    }
}
