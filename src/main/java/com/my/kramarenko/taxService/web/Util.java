package com.my.kramarenko.taxService.web;

import com.my.kramarenko.taxService.db.dao.UserDAO;
import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.entity.UserDetails;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.exception.XmlException;
import com.my.kramarenko.taxService.web.command.LastPage;
import com.my.kramarenko.taxService.xml.ReadXmlDOMController;
import com.my.kramarenko.taxService.xml.entity.ReportValue;
import com.my.kramarenko.taxService.xml.entity.SubElement;
import com.my.kramarenko.taxService.xml.entity.TaxForm;
import com.my.kramarenko.taxService.xml.forms.ReportForm;
import com.my.kramarenko.taxService.xml.forms.ReportFormContainer;
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

public class Util {
    private static final Logger LOG = Logger.getLogger(Util.class);

    public static int getIntValue(int defaultValue, String value) {
        if (value != null) {
            try {
                defaultValue = Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return defaultValue;
    }

    public static void setReportsWithPagination(List<?> reportsList, HttpServletRequest request) {
        int recordsPerPage = Util.getIntValue(10, request.getParameter("recordsPerPage"));
        int selectedPage = Util.getIntValue(1, request.getParameter("selectedPage"));

        LOG.trace("selected page = " + selectedPage);
        int maxReport = selectedPage * recordsPerPage;
        if (reportsList.size() < selectedPage * recordsPerPage) maxReport = reportsList.size();
        int minReport = (selectedPage - 1) * recordsPerPage;
        LOG.trace("show users from " + minReport + " to " + maxReport);
        int noOfPages = (int) Math.ceil((double) reportsList.size() / recordsPerPage);
        LOG.trace("noOfPages = " + noOfPages);
        List<?> chosenReports = reportsList.subList(minReport, maxReport);
        LOG.trace(chosenReports);
        request.setAttribute("paginationList", chosenReports);

        request.setAttribute("noOfPages", noOfPages);

        request.setAttribute("recordsPerPage", recordsPerPage);
        LOG.trace("set recordsPerPage=" + recordsPerPage);
        request.setAttribute("selectedPage", selectedPage);
        LOG.trace("set selectedPage=" + selectedPage);
    }

    public static void resetAvailableError(HttpServletRequest request) {
        String error = request.getParameter("error");
        if (error != null && error.length() > 0)
            request.setAttribute("errorMessage", error);
    }

    public static String updateLastPageIfEmpty(HttpServletRequest request, String currentPage) {
        String lastPage = LastPage.getPage((String) request.getSession().getAttribute("page"));
        if (lastPage.isEmpty()) {
            LOG.trace("Last page is empty => set " + currentPage + " page as last page");
            lastPage = currentPage;
            request.getSession().setAttribute("page", lastPage);
        }
        return lastPage;
    }

    public static void putReportToRequest(Report report, HttpServletRequest request) throws XmlException {
        String reportTypeId = report.getTypeId();
        String fileName = report.getXmlPath();
        ReadXmlDOMController domController = new ReadXmlDOMController();
        ReportForm reportForm = ReportFormContainer.getForm(reportTypeId);
        TaxForm taxForm = domController.loadFile(reportForm, fileName);
        setReportParametersToRequest(taxForm, request);
        request.setAttribute("reportId", report.getId());
        request.setAttribute("reportStatusId", report.getStatusId());
    }

    public static void setReportParametersToRequest(TaxForm taxForm, HttpServletRequest request) {
        for (Map.Entry<String, List> entry : taxForm.getDeclarHead().getEntrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue().get(0));
        }
        LOG.trace("Declarhead parameters are set");
        for (Map.Entry<String, List> entry : taxForm.getDeclarBody().getEntrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue().get(0));
        }
        LOG.trace("Declarbody parameters are set");
    }

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
            LOG.error(e.getMessage(), e);
            throw new XmlException("can't create download link", e);
        }
    }

    public static void setUserFieldsFromRequest(User editableUser, HttpServletRequest request) {
        editableUser.setEmail(request.getParameter("email"));
        editableUser.setCode(request.getParameter("code"));
        editableUser.setCompanyName(request.getParameter("company_name"));
        try {
            editableUser.setLegalType(Integer.parseInt(request.getParameter("legalType")));
        } catch (Exception e) {
            LOG.trace("legal type is empty");
        }
    }

    public static void setUserDetailsFieldsFromRequest(UserDetails editableUser, HttpServletRequest request) {
        editableUser.setFirstName(request.getParameter("first_name"));
        editableUser.setLastName(request.getParameter("last_name"));
        editableUser.setPatronymic(request.getParameter("patronymic"));
        editableUser.setPhone(request.getParameter("phone"));
    }

    public static List<User> getUserListDependOnFilterInRequest(HttpServletRequest request) throws DBException {
        UserDAO userManager = DBManager.getInstance().getUserDAO();
        List<User> users;
        String userFilter = request.getParameter("userFilter");
        request.setAttribute("userFilter", userFilter);
        if (userFilter == null || userFilter.length() == 0) {
            users = userManager.getAllUsers();
        } else {
            users = userManager.getUsersByFilter(userFilter);
        }
        return users;
    }

    public static String getValue(String value) {
        if (value != null) return value;
        else return "";
    }

    public static String createCompanyName(UserDetails userDetails) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getValue(userDetails.getLastName()));
        stringBuilder.append(" ");
        stringBuilder.append(getValue(userDetails.getFirstName()));
        stringBuilder.append(" ");
        stringBuilder.append(getValue(userDetails.getPatronymic()));
        return stringBuilder.toString();
    }
}
