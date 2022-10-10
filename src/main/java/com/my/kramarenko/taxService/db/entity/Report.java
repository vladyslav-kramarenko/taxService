package com.my.kramarenko.taxService.db.entity;

import java.sql.Timestamp;

public class Report {

    private int id;
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
                ", statusId=" + statusId +
                ", typeId=" + typeId + '\'' +
                '}';
    }
}
