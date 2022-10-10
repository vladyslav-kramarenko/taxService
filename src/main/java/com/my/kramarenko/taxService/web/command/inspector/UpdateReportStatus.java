package com.my.kramarenko.taxService.web.command.inspector;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.entity.Status;
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
public class UpdateReportStatus extends Command {

    private static final long serialVersionUID = -3071536594787692473L;

    private static final Logger LOG = Logger.getLogger(UpdateReportStatus.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException {

        LOG.debug("Command starts");
        String reportIdParameter = request.getParameter("reportId");
        if (reportIdParameter != null && !reportIdParameter.isEmpty()) {
            int reportId = Integer.parseInt(reportIdParameter);
            LOG.trace("reportId = " + reportId);

            String comment = request.getParameter("comment");
            String statusParam = request.getParameter("status");
            LOG.trace("statusId: " + statusParam + "; comment: " + comment);

            ServletContext sc = request.getServletContext();
            Map<Integer, Status> statusMap = (Map<Integer, Status>) sc.getAttribute("statusMap");

            Status status = statusMap.get(Integer.parseInt(statusParam));//Status.getStatus(Integer.parseInt(statusParam));
            LOG.trace("status: " + status);

            ReportManager rm = new ReportManager();
            rm.updateReportStatus(reportId, status, comment);
        } else {
            LOG.trace("reportId is null");
        }
        LOG.trace("Command finished");
        response.sendRedirect(Path.COMMAND_REPORT_LIST);
        return null;
    }

//    private static TaxForm getTaxForm(ReportForm reportForm, HttpServletRequest request) {
//        TaxForm taxForm = new TaxForm();
//
//        SubElement declarHead = new SubElement();
//
//        for (ReportValue entity : reportForm.getHead()) {
//            String parameter = request.getParameter(entity.name);
//            if (parameter != null) {
//                List list = List.of(parameter);
//                declarHead.addParameter(entity.name, list);
//            }
//        }
//        taxForm.setDeclarHead(declarHead);
//
//        SubElement declarBody = new SubElement();
//        for (ReportValue entity : reportForm.getBody()) {
//            String parameter = request.getParameter(entity.name);
//            if (parameter != null) {
//                List list = List.of(parameter);
//                declarBody.addParameter(entity.name, list);
//            }
//        }
//        taxForm.setDeclarBody(declarBody);
//
//        return taxForm;
//    }
//
//    private static TaxForm getReport(HttpServletRequest request) {
//        TaxForm taxForm = new TaxForm();
//        return taxForm;
//    }

//    private void error(HttpServletRequest request, String errorMessage) {
//        request.setAttribute("errorMessage", errorMessage);
//        LOG.error("errorMessage --> " + errorMessage);
//    }
}