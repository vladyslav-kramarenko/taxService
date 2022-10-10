package com.my.kramarenko.taxService.web.tags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

public class MyTag extends SimpleTagSupport {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void doTag() throws JspException, IOException {

        getJspContext().getOut().print("some" + name);
    }
}