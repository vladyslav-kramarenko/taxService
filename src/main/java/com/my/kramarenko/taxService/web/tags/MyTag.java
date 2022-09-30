package com.my.kramarenko.taxService.web.tags;

import jakarta.servlet.jsp.*;
//import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class HelloTag extends SimpleTagSupport {

    public void doTag() throws JspException, IOException {
        // <%
        // String site = new String("controller?command=productList");
        // response.setStatus(response.SC_MOVED_TEMPORARILY);
        // response.setHeader("Location", site);
        // %>
        JspWriter out = getJspContext().getOut();
        out.println("Hello Custom Tag!");


    }
}