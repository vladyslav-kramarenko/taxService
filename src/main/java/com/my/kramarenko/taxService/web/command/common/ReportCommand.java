package com.my.kramarenko.taxService.web.command.common;

import com.my.kramarenko.taxService.db.entity.UserDetails;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.exception.XmlException;
import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.Path;
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
public class ReportCommand extends Command {

    @Serial
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(ReportCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException, XmlException {

        LOG.trace("Command starts");
        String forward = Path.PAGE_REPORT;

        ServletContext sc = request.getServletContext();
//        List<Status> statuses = (List<Status>) sc.getAttribute("allStatuses");
        Map<Integer, Status> statusMap = (Map<Integer, Status>) sc.getAttribute("statusMap");

        Map<Integer, Role> roleMap = (Map<Integer, Role>) sc.getAttribute("roleMap");

        User user = (User) request.getSession().getAttribute("user");

        Role userRole = roleMap.get(user.getRoleId());
        Status status = Status.DRAFT;
        String typeId = request.getParameter("reportTypeId");
        String reportIdParameter = request.getParameter("reportId");
        LOG.trace(request.getRequestURI());

        if (reportIdParameter != null && !reportIdParameter.isEmpty()) {
            int reportId = Integer.parseInt(reportIdParameter);
            Report report = DBManager.getInstance().getReportDAO().getReport(reportId);
            putReportToRequest(report, request);

            typeId = report.getTypeId();
            status = statusMap.get(report.getStatusId());//Status.getStatus(report.getStatusId());
            request.setAttribute("reportComment", report.getComment());
        } else {
            UserDetails userDetails=DBManager.getInstance().getUserDAO().getUserDetails(user.getId());
            request.setAttribute("HTEL", userDetails.getPhone());
        }

        request.setAttribute("reportStatus", status);
        if (userRole.equals(Role.USER) && (status.equals(Status.DRAFT) || status.equals(Status.NOT_ACCEPTED))) {
            request.setAttribute("editable", "true");
//            LOG.trace("editable=true");
        } else {
            request.setAttribute("editable", "false");
        }


        if (userRole.equals(Role.INSPECTOR)) {
            Status[] statusValues = {Status.ACCEPTED, Status.NOT_ACCEPTED};
            request.setAttribute("statusTypes", statusValues);
            LOG.trace("set report statuses to request attribute: \n" + Arrays.toString(statusValues));
        }

        LOG.trace("reportTypeId = " + typeId);
        request.setAttribute("reportTypeId", typeId);
//        request.getSession().setAttribute("page", Path.PAGE_REPORT);
        LOG.trace("Command finished");
        return forward;
    }
}