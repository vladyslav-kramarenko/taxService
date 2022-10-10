package com.my.kramarenko.taxService.db.dto;

import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.Type;
import com.my.kramarenko.taxService.db.entity.Status;

public class ReportDTO {
    Report report;
    Type type;
    Status status;

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
