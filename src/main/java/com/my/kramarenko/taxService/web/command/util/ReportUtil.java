package com.my.kramarenko.taxService.web.command.util;

import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.exception.XmlException;
import com.my.kramarenko.taxService.xml.entity.ReportValue;
import com.my.kramarenko.taxService.xml.entity.SubElement;
import com.my.kramarenko.taxService.xml.entity.TaxForm;
import com.my.kramarenko.taxService.xml.forms.ReportForm;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportUtil {
    private static final Logger LOG = Logger.getLogger(ReportUtil.class);

    public static Map<Status, Boolean> getChosenStatusMap(Map<Integer, Status> statusMap, String[] chosedId) {
        Map<Status, Boolean> chosenStatuses;
        if (chosedId != null) {
            chosenStatuses = statusMap.values().stream().collect(Collectors.toMap(x -> x, x -> false));
            for (String s : chosedId) {
                int id = Integer.parseInt(s);
                chosenStatuses.put(statusMap.get(id), true);
            }
        } else {
            chosenStatuses = statusMap.values().stream().collect(Collectors.toMap(x -> x, x -> true));
        }
        return chosenStatuses;
    }


    public static TaxForm getTaxFormFromRequest(ReportForm reportForm, HttpServletRequest request) {
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

    public static void printXmlToResponse(String xml, HttpServletResponse response) throws XmlException {
        try (ServletOutputStream out = response.getOutputStream();
             InputStream in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))) {

            response.reset();
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment; filename=report.xml");
            response.setContentLength(xml.getBytes(StandardCharsets.UTF_8).length);

            int readBytes;
            while ((readBytes = in.read()) != -1)
                out.write(readBytes);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new XmlException("can't create download link", e);
        }
    }
}
