package com.my.kramarenko.taxService.db.dto;

import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.Type;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Status;

public class ReportDTO {

    private User user;
    private Report report;
    private Type type;
    private Status status;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    @Override
    public String toString() {
        return "ReportDTO{" +
                "user=" + user +
                ", report=" + report +
                ", type=" + type +
                ", status=" + status +
                '}';
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
