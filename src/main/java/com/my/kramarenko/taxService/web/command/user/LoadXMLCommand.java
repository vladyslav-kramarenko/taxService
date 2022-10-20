package com.my.kramarenko.taxService.web.command.user;

import com.my.kramarenko.taxService.db.XmlException;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.xml.forms.ReportFormContainer;
import com.my.kramarenko.taxService.xml.ReadXmlDOMController;
import com.my.kramarenko.taxService.xml.forms.ReportForm;
import com.my.kramarenko.taxService.xml.entity.TaxForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LoadXMLCommand extends Command {

    private static final long serialVersionUID = 1863978254689586513L;

    private static final Logger LOG = Logger.getLogger(LoadXMLCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws XmlException {
        LOG.debug("Command starts");
        try {
            Part part = request.getPart("file");
            String fileName = part.getSubmittedFileName();
            String typeId = request.getParameter("reportTypeId");
            LOG.debug("Load file: " + fileName);

            ReadXmlDOMController domController = new ReadXmlDOMController();
            ReportForm reportForm = ReportFormContainer.getForm(typeId);
//            ReportForm reportForm = new F0103405();
            TaxForm taxForm = domController.loadInputStream(reportForm, part.getInputStream());
            LOG.trace("file loaded");

            setParameters(taxForm, request);
            request.setAttribute("reportTypeId", typeId);
            LOG.debug("Command finished");
            return Path.PAGE_REPORT;
//            return Path.COMMAND_REPORT;
        } catch (XmlException | IOException | ServletException e) {
            LOG.error(e.getMessage());
            throw new XmlException("Can't parse the XML file: ", e);
        }
    }

    private void setParameters(TaxForm taxForm, HttpServletRequest request) {
        for (Map.Entry<String, List> entry : taxForm.getDeclarHead().getEntrySet()) {
            LOG.debug(entry.getKey() + ": " + entry.getValue().get(0));
            request.setAttribute(entry.getKey(), entry.getValue().get(0));
        }
        for (Map.Entry<String, List> entry : taxForm.getDeclarBody().getEntrySet()) {
            LOG.debug(entry.getKey() + ": " + entry.getValue().get(0));
            request.setAttribute(entry.getKey(), entry.getValue().get(0));
        }
    }
}
