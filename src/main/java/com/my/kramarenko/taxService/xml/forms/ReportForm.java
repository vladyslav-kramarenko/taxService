package com.my.kramarenko.taxService.xml.forms;

import com.my.kramarenko.taxService.xml.entity.ReportValue;

import java.util.*;

public abstract class ReportForm {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportForm that = (ReportForm) o;

        return (reportType.equals(that.reportType));
    }

    @Override
    public int hashCode() {
        return reportType.hashCode();
    }

    private String reportType;
    private List<ReportValue> head = new LinkedList<>();
    private List<ReportValue> body = new LinkedList<>();

    public void setHead(List<ReportValue> head) {
        this.head = head;
    }

    public void setBody(List<ReportValue> body) {
        this.body = body;
    }


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
