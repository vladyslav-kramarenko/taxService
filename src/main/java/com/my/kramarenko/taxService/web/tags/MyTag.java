package com.my.kramarenko.taxService.web.tags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

public class MyTag extends TagSupport {
//    public class MyTag extends SimpleTagSupport {

    private String pageQuantity;

    public void setPageQuantity(String pageQuantity) {
        this.pageQuantity = pageQuantity;
    }

    //    @Override
//    public void doTag() throws JspException, IOException {
//        getJspContext().getOut().print("some[" + pageQuantity + "]");
//    }
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            out.print(pageQuantity);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return SKIP_BODY;
    }
}