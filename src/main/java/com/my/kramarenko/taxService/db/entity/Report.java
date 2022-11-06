package com.my.kramarenko.taxService.db.entity;

import java.sql.Timestamp;

public class Report {

    private int id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report report = (Report) o;

        if (id != report.id) return false;
        if (statusId != report.statusId) return false;
        if (!date.equals(report.date)) return false;
        if (!lastUpdate.equals(report.lastUpdate)) return false;
        return typeId.equals(report.typeId);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + date.hashCode();
        result = 31 * result + lastUpdate.hashCode();
        result = 31 * result + statusId;
        result = 31 * result + typeId.hashCode();
        return result;
    }

    private Timestamp date;

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    private Timestamp lastUpdate;
    private int statusId;
    private String typeId;
    private String xmlPath;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String comment;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }


    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }


    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", date=" + date +
                ", lastUpdate=" + date +
                ", statusId=" + statusId +
                ", typeId=" + typeId + '\'' +
                '}';
    }
}
