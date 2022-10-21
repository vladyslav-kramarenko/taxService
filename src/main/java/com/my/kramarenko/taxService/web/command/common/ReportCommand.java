package com.my.kramarenko.taxService.web.command.common;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.XmlException;
import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.db.mySQL.ReportManager;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.xml.ReadXmlDOMController;
import com.my.kramarenko.taxService.xml.forms.ReportForm;
import com.my.kramarenko.taxService.xml.forms.ReportFormContainer;
import com.my.kramarenko.taxService.xml.entity.TaxForm;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Login command.
 *
 * @author Vlad Kramarenko
 */
public class ReportCommand extends Command {

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

        Status status = statusMap.get(1);//Status.DRAFT;
        String typeId = request.getParameter("reportTypeId");
        String reportIdParameter = request.getParameter("reportId");
        LOG.trace(request.getRequestURI());

        if (reportIdParameter != null && !reportIdParameter.isEmpty()) {
            int reportId = Integer.parseInt(reportIdParameter);
            Report report = openSavedReport(reportId, request);
            typeId = report.getTypeId();
            status = statusMap.get(report.getStatusId());//Status.getStatus(report.getStatusId());
        } else {
            request.setAttribute("HTEL", user.getPhone());
        }

        request.setAttribute("reportStatus", status);


        if (roleMap.get(user.getRoleId()).equals(Role.INSPECTOR)) {
            Status[] statusValues = {Status.SENT, Status.ACCEPTED, Status.REFUSED};
//            Status[] statusValues = {Status.SENT, Status.ACCEPTED, Status.REFUSED};
            request.setAttribute("statusTypes", statusValues);
            LOG.trace("set report statuses to request attribute: \n" + Arrays.toString(statusValues));
        }

        LOG.trace("reportTypeId = " + typeId);
        request.setAttribute("reportTypeId", typeId);
//        request.getSession().setAttribute("page", Path.PAGE_REPORT);
        LOG.trace("Command finished");
        return forward;
    }

    private Report openSavedReport(int reportId, HttpServletRequest request) throws XmlException, DBException {
        ReportManager rm = new ReportManager();
        Report report = rm.getReport(reportId);
        String fileName = report.getXmlPath();

        ReadXmlDOMController domController = new ReadXmlDOMController();

        String reportTypeId = report.getTypeId();
        ReportForm reportForm = ReportFormContainer.getForm(reportTypeId);

        TaxForm taxForm = domController.loadFile(reportForm, fileName);
        setParameters(taxForm, request);
        request.setAttribute("reportId", reportId);
        request.setAttribute("reportStatusId", report.getStatusId());
        return report;
    }

    private void setParameters(TaxForm taxForm, HttpServletRequest request) {
        for (Map.Entry<String, List> entry : taxForm.getDeclarHead().getEntrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue().get(0));
        }
        LOG.trace("Declarhead parameters are set");
        for (Map.Entry<String, List> entry : taxForm.getDeclarBody().getEntrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue().get(0));
        }
        LOG.trace("Declarbody parameters are set");
    }
}