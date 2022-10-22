package com.my.kramarenko.taxService.web.command.user;

import com.my.kramarenko.taxService.db.dao.ReportDAO;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.xml.forms.*;
import com.my.kramarenko.taxService.xml.entity.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serial;
import java.util.List;

/**
 * Login command.
 *
 * @author Vlad Kramarenko
 */
public class SubmitReportCommand extends Command {

    @Serial
    private static final long serialVersionUID = -3071536594787692473L;

    private static final Logger LOG = Logger.getLogger(SubmitReportCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException {

        LOG.debug("Command starts");
        String reportName = request.getParameter("reportName");
        ReportForm reportForm = ReportFormContainer.getForm(reportName);

        TaxForm taxForm = getTaxForm(reportForm, request);

        User user = (User) request.getSession().getAttribute("user");
        ReportDAO reportDAO = DBManager.getInstance().getReportDAO();
        int reportID = -1;
        String reportIdParam = request.getParameter("reportId");
        try {
            if (reportIdParam != null && !reportIdParam.isEmpty()) {
                reportID = Integer.parseInt(reportIdParam);
            }
        } catch (Exception e) {
            LOG.error("incorrect report ID:" + reportIdParam);
        }
        String save = request.getParameter("save");
        String send = request.getParameter("send");

        Status status = Status.DRAFT;
        if (save == null) {
            LOG.trace("action = " + send);
            status = Status.SENT;
        } else {
            LOG.trace("action = " + save);
        }
        if (reportID >= 0) {
            LOG.trace("obtain report id: " + reportID + " => edit current report");
            reportDAO.editReport(status, reportID, taxForm, reportForm);
        } else {
            LOG.trace("current report id is empty => create new report");
            reportDAO.addReport(status, user, taxForm, reportForm);
        }

        LOG.trace("Command finished");
//        response.sendRedirect(Path.COMMAND_REPORT_LIST);
        return Path.COMMAND_REPORT_LIST;
//        return null;
    }

    private static TaxForm getTaxForm(ReportForm reportForm, HttpServletRequest request) {
        TaxForm taxForm = new TaxForm();

        SubElement declarHead = new SubElement();


        for (ReportValue entity : reportForm.getHead()) {
            String parameter = request.getParameter(entity.name);
            if (parameter != null) {
                List list = List.of(parameter);
                declarHead.addParameter(entity.name, list);
            }
        }
        taxForm.setDeclarHead(declarHead);

        SubElement declarBody = new SubElement();
        for (ReportValue entity : reportForm.getBody()) {
            String parameter = request.getParameter(entity.name);
            if (parameter != null) {
                List list = List.of(parameter);
                declarBody.addParameter(entity.name, list);
            }
        }
        taxForm.setDeclarBody(declarBody);

        return taxForm;
    }
}