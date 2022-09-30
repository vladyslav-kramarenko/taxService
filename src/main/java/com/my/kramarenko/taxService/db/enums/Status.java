package com.my.kramarenko.taxService.db.enums;


public enum ReportStatus {
    CREATED(1), SENT(2), ACCEPTED(3), REFUSED(4);
    int id;

    ReportStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ReportStatus getStatus(int id) {
        return ReportStatus.values()[id];
    }
}