package com.my.kramarenko.taxService.web.command.user;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.dao.ReportDao;
import com.my.kramarenko.taxService.db.entity.Status;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.db.mySQL.ReportManager;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.xml.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Login command.
 *
 * @author Vlad Kramarenko
 */
public class DeleteReportCommand extends Command {

    private static final long serialVersionUID = -3071536594787692473L;

    private static final Logger LOG = Logger.getLogger(DeleteReportCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException {

        LOG.debug("Command starts");
        User user = (User) request.getSession().getAttribute("user");
        int reportID = -1;
        String reportIdParam = request.getParameter("reportId");
        try {
            if (reportIdParam != null && !reportIdParam.isEmpty()) {
                reportID = Integer.parseInt(reportIdParam);
            }
        } catch (Exception e) {
            LOG.error("incorrect report ID:" + reportIdParam);
        }

        if (reportID >= 0) {
            ServletContext sc = request.getServletContext();
            Map<Integer, Status> statusMap = (Map<Integer, Status>) sc.getAttribute("statusMap");
            LOG.trace("obtain report id: " + reportID + " => delete current report");
            DBManager.getInstance().getReportManager().updateReportStatus(reportID, statusMap.get(2));
        } else {
            LOG.trace("incorrect report id");
        }

        LOG.trace("Command finished");
        response.sendRedirect(Path.COMMAND_REPORT_LIST);
        return null;
    }
}