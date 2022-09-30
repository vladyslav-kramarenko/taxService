package com.my.kramarenko.taxService.web.command.user;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.XmlException;
import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.db.mySQL.ReportManager;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.xml.ReadXmlDOMController;
import com.my.kramarenko.taxService.xml.ReportForm;
import com.my.kramarenko.taxService.xml.ReportFormContainer;
import com.my.kramarenko.taxService.xml.TaxForm;
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
        Status reportStatus = Status.DRAFT;

        String reportIdParameter = request.getParameter("reportId");
        LOG.trace(request.getRequestURI());
        if (reportIdParameter != null && !reportIdParameter.isEmpty()) {
            int reportId = Integer.parseInt(reportIdParameter);

            ReportManager rm = new ReportManager();
            Report report = rm.getReport(reportId);
            String fileName = report.getXmlPath();
            reportStatus = Status.getStatus(report.getStatusId());

            ReadXmlDOMController domController = new ReadXmlDOMController();

            String reportTypeId = report.getTypeId();
            ReportForm reportForm = ReportFormContainer.getForm(reportTypeId);

            TaxForm taxForm = domController.loadFile(reportForm, fileName);
            setParameters(taxForm, request);
            request.setAttribute("reportId", reportId);
            request.setAttribute("reportStatusId", report.getStatusId());
        }

        if (Role.getRole((User) request.getSession().getAttribute("user")).equals(Role.INSPECTOR)) {
            request.setAttribute("statusTypes", Status.values());
            LOG.trace("set report statuses to request attribute: \n" + Arrays.toString(Status.values()));
        }
        request.setAttribute("reportStatus", reportStatus);
        request.getSession().setAttribute("page", Path.PAGE_REPORT);
        LOG.trace("Command finished");
        return forward;
    }

    private void setParameters(TaxForm taxForm, HttpServletRequest request) {
        for (Map.Entry<String, List> entry : taxForm.getDeclarHead().getEntrySet()) {
//            LOG.debug(entry.getKey() + ": " + entry.getValue().get(0));
            request.setAttribute(entry.getKey(), entry.getValue().get(0));
//            request.getSession().setAttribute(entry.getKey(), entry.getValue().get(0));
        }
        LOG.trace("Declarhead parameters are set");
        for (Map.Entry<String, List> entry : taxForm.getDeclarBody().getEntrySet()) {
//            LOG.debug(entry.getKey() + ": " + entry.getValue().get(0));
            request.setAttribute(entry.getKey(), entry.getValue().get(0));
//            request.getSession().setAttribute(entry.getKey(), entry.getValue().get(0));
        }
        LOG.trace("Declarbody parameters are set");
    }
}