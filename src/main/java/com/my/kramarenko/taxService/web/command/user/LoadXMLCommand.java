package com.my.kramarenko.taxService.web.command.user;

import com.my.kramarenko.taxService.exception.XmlException;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.xml.forms.ReportFormContainer;
import com.my.kramarenko.taxService.xml.ReadXmlDOMController;
import com.my.kramarenko.taxService.xml.forms.ReportForm;
import com.my.kramarenko.taxService.xml.entity.TaxForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serial;
import java.util.List;
import java.util.Map;

public class LoadXMLCommand extends Command {

    @Serial
    private static final long serialVersionUID = 1863978254689586513L;
    private static final String TAX_FORM_ATTRIBUTE_NAME = "taxForm";
    private static final String REPORT_TYPE_ID_ATTRIBUTE_NAME = "reportTypeId";


    private static final Logger LOG = Logger.getLogger(LoadXMLCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws XmlException {
        LOG.debug("Command starts");
        if (request.getMethod().equals("POST")) {
            try {
                Part part = request.getPart("file");
                String fileName = part.getSubmittedFileName();
                String typeId = request.getParameter(REPORT_TYPE_ID_ATTRIBUTE_NAME);
                LOG.debug("Load file: " + fileName);

                ReadXmlDOMController domController = new ReadXmlDOMController();
                ReportForm reportForm = ReportFormContainer.getForm(typeId);
                TaxForm taxForm = domController.loadInputStream(reportForm, part.getInputStream());
                LOG.trace("file loaded");
                HttpSession session = request.getSession();
                session.setAttribute(TAX_FORM_ATTRIBUTE_NAME, taxForm);
                return Path.COMMAND_LOAD_XML + "&" + REPORT_TYPE_ID_ATTRIBUTE_NAME + "=" + typeId;
            } catch (XmlException | IOException | ServletException e) {
                LOG.error(e.getMessage());
                throw new XmlException("Can't parse the XML file: ", e);
            }
        }

        HttpSession session = request.getSession();
        TaxForm taxForm = (TaxForm) session.getAttribute(TAX_FORM_ATTRIBUTE_NAME);
        session.removeAttribute(TAX_FORM_ATTRIBUTE_NAME);

        String typeId = request.getParameter(REPORT_TYPE_ID_ATTRIBUTE_NAME);
        request.setAttribute(REPORT_TYPE_ID_ATTRIBUTE_NAME, typeId);

        setParameters(taxForm, request);

        LOG.debug("Command finished");
        return Path.PAGE_REPORT;
    }

    private void setParameters(TaxForm taxForm, HttpServletRequest request) {
        LOG.trace("set XML Parameters");
        for (Map.Entry<String, List> entry : taxForm.getDeclarHead().getEntrySet()) {
//            LOG.debug(entry.getKey() + ": " + entry.getValue().get(0));
            request.setAttribute(entry.getKey(), entry.getValue().get(0));
        }
        for (Map.Entry<String, List> entry : taxForm.getDeclarBody().getEntrySet()) {
//            LOG.debug(entry.getKey() + ": " + entry.getValue().get(0));
            request.setAttribute(entry.getKey(), entry.getValue().get(0));
        }
    }
}
