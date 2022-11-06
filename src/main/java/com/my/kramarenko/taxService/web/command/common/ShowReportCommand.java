package com.my.kramarenko.taxService.web.command.common;

import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.exception.CommandException;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.exception.XmlException;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.Command;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serial;
import java.util.Arrays;
import java.util.Map;

import static com.my.kramarenko.taxService.web.Util.putReportToRequest;

/**
 * Login command.
 *
 * @author Vlad Kramarenko
 */
public class ShowReportCommand extends Command {

    @Serial
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(ShowReportCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException, XmlException, CommandException {

        LOG.trace("Command starts");
        try {
            String forward = Path.ADMIN_REPORT;

            ServletContext sc = request.getServletContext();
            Map<Integer, Status> statusMap = (Map<Integer, Status>) sc.getAttribute("statusMap");
            Map<Integer, Role> roleMap = (Map<Integer, Role>) sc.getAttribute("roleMap");

            int reportId = Integer.parseInt(request.getParameter("reportId"));
            LOG.trace("received report id = "+reportId);

            Report report = DBManager.getInstance().getReportDAO().getReport(reportId);
            putReportToRequest(report, request);

            Status status = Status.getStatus(report.getStatusId());
            request.setAttribute("reportStatus", status);
            LOG.trace("set report status to request: " + status.getName());

            User user = (User) request.getSession().getAttribute("user");
            if (roleMap.get(user.getRoleId()).equals(Role.INSPECTOR)) {
                Status[] statusValues = {Status.SUBMITTED, Status.ACCEPTED, Status.NOT_ACCEPTED};
                request.setAttribute("statusTypes", statusValues);
                LOG.trace("set report statuses to request attribute: \n" + Arrays.toString(statusValues));
                forward = Path.INSPECTOR_REPORT;
            }

            request.setAttribute("reportTypeId", statusMap.get(report.getTypeId()));
//        request.getSession().setAttribute("page", Path.PAGE_REPORT);
            LOG.trace("Command finished");
            return forward;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new CommandException("Report not founded", e);
        }
    }
}