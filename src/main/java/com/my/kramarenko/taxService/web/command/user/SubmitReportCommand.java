package com.my.kramarenko.taxService.web.command.user;

import com.my.kramarenko.taxService.web.Util;
import com.my.kramarenko.taxService.db.dao.ReportDAO;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.exception.XmlException;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.xml.WriteXmlStAXController;
import com.my.kramarenko.taxService.xml.forms.*;
import com.my.kramarenko.taxService.xml.entity.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import java.io.*;

import static com.my.kramarenko.taxService.web.Util.getTaxFormFromRequest;
import static com.my.kramarenko.taxService.web.Util.printXmlToResponse;

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
                          HttpServletResponse response) throws ServletException {
        LOG.debug("Command starts");

        Status status = getStatus(request);
        if (status == null) {
            throw new ServletException("Cannot submit the report");
        }

        try {
            String reportTypeId = request.getParameter("reportTypeId");
            ReportForm reportForm = ReportFormContainer.getForm(reportTypeId);
            TaxForm taxForm = getTaxFormFromRequest(reportForm, request);

            User user = (User) request.getSession().getAttribute("user");

            int reportID = Util.getIntValue(-1, request.getParameter("reportId"));
            ReportDAO reportDAO = DBManager.getInstance().getReportDAO();
            if (reportID >= 0) {
                LOG.trace("obtain report id: " + reportID + " => edit current report");
                reportDAO.editReport(status, reportID, taxForm, reportForm);
            } else {
                LOG.trace("current report id is empty => create new report");
                reportDAO.addReport(status, user, taxForm, reportForm);
            }
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            throw new ServletException("Cannot submit the report", e);
        }
        return Path.COMMAND_REPORT_LIST;
    }

    private static Status getStatus(HttpServletRequest request) {
        String command = request.getParameter("command");
        String save = request.getParameter("save");
        String send = request.getParameter("send");
        if (command.equals("submitReport")) {
            LOG.trace("action = " + send);
            return Status.SUBMITTED;
        }
        if (command.equals("saveReport")) {
            LOG.trace("action = " + save);
            return Status.DRAFT;
        }
        return null;
    }
}