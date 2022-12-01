package com.my.kramarenko.taxService.web.command.user;

import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.db.DBManager;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serial;

import static com.my.kramarenko.taxService.web.mail.MailCreator.createReportUpdateNotification;

/**
 * Login command.
 *
 * @author Vlad Kramarenko
 */
public class CancelReportCommand extends Command {

    @Serial
    private static final long serialVersionUID = -3071536594787692473L;

    private static final Logger LOG = Logger.getLogger(CancelReportCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException {

        LOG.trace("Command starts");
        int reportID = -1;
        String reportIdParam = request.getParameter("reportId");
        try {
            if (reportIdParam != null && !reportIdParam.isEmpty()) {
                reportID = Integer.parseInt(reportIdParam);
            }
        } catch (Exception e) {
            LOG.error("incorrect report ID:" + reportIdParam, e);
        }

        if (reportID >= 0) {
            LOG.trace("obtain report id: " + reportID + " => cancel current report");
            DBManager.getInstance().getReportDAO().updateReportStatus(reportID, Status.DRAFT);
            createReportUpdateNotification(request, reportID);
        } else {
            LOG.trace("incorrect report id");
        }

        LOG.trace("Command finished");
        return Path.COMMAND_REPORT_LIST;
    }
}