package com.my.kramarenko.taxService.xml;

import java.util.*;

public abstract class ReportForm {
    private String reportType;

    public void setHead(List<ReportValue> head) {
        this.head = head;
    }

    public void setBody(List<ReportValue> body) {
        this.body = body;
    }

    private List<ReportValue> head = new LinkedList<>();
    private List<ReportValue> body = new LinkedList<>();

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<ReportValue> getHead() {
        return head;
    }

    public void addHeadValue(ReportValue headValue) {
        head.add(headValue);
    }

    public List<ReportValue> getBody() {
        return body;
    }

    public void addBodyValue(ReportValue bodyValue) {
        body.add(bodyValue);
    }

}
