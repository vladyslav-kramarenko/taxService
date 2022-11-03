package com.my.kramarenko.taxService.web.tags;

import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

public class RecordsPerPageChooserTag extends SimpleTagSupport {
    private String pageQuantity;
    private String recordsPerPage;
    private String pageCommand;

    public void setPageCommand(String pageCommand) {
        this.pageCommand = pageCommand;
    }

    public void setPageQuantity(String pageQuantity) {
        this.pageQuantity = pageQuantity;
    }

    public void setRecordsPerPage(String recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        out.print("<span id='recordsPerPageSpan'>");
        out.print("<label for='recordsPerPage'> количество записей на странице:</label>");
        out.print("<input type='hidden' name='command' value='" + pageCommand + "'>");
        out.print("<input type='hidden' name='selectedPage' value='1'>");
        out.print("<select id='recordsPerPage' name='recordsPerPage' class='select' onchange='this.form.submit()'>");

        String[] quantities = pageQuantity.split(",");
        for (String quantity : quantities) {
            try {
                if (recordsPerPage.equals(quantity)) {
                    out.print("<option selected value='" + quantity + "'>" + quantity + "</option>");
                } else {
                    out.print("<option value='" + quantity + "'>" + quantity + "</option>");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        out.print("</select>");
        out.print("</span>");
    }
}