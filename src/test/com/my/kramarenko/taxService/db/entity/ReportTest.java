package com.my.kramarenko.taxService.db.entity;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    @Test
    void lastUpdateTest() {
        Report report = new Report();
        long time = 9999999999L;
        report.setLastUpdate(new Timestamp(time));
        assertTrue(new Timestamp(time).equals(report.getLastUpdate()));
        assertFalse(new Timestamp(0).equals(report.getLastUpdate()));
    }


    @Test
    void idTest() {
        Report report = new Report();
        int id = 1;
        report.setId(id);
        assertEquals(id,report.getId());
        assertNotEquals(0, report.getId());
    }


    @Test
    void dateTest() {
        Report report = new Report();
        long time = 9999999999L;
        report.setDate(new Timestamp(time));
        assertEquals(new Timestamp(time),(report.getDate()));
        assertNotEquals(new Timestamp(0),(report.getDate()));
    }


    @Test
    void statusTest() {
        Report report = new Report();
        int statusId = 1;
        report.setStatusId(statusId);
        assertEquals(statusId,report.getStatusId());
        assertNotEquals( 0,report.getStatusId());
    }


    @Test
    void typeTest() {
        Report report = new Report();
        String typeId = "123";
        report.setTypeId(typeId);
        assertEquals(typeId,report.getTypeId());
        assertNotEquals("",report.getTypeId());
    }


    @Test
    void xmlPathTest() {
        Report report = new Report();
        String xmlPath = "path";
        report.setXmlPath(xmlPath);
        assertEquals(xmlPath,(report.getXmlPath()));
        assertNotEquals("",(report.getXmlPath()));
    }


    @Test
    void testToString() {
        Report report = new Report();
        String reportString = "Report{" +
                "id=" + 0 +
                ", date=" + "null" +
                ", lastUpdate=" + "null" +
                ", statusId=" + 0 +
                ", typeId=" + "null" + '\'' +
                '}';
        assertEquals(reportString, report.toString());

        int id = 0;
        long dateSeconds = 999999L;
        Timestamp date = new Timestamp(dateSeconds);
        Timestamp lastUpdate = new Timestamp(dateSeconds + 1);
        int statusId = 1;
        String typeId = "5";
        report.setDate(date);
        report.setId(id);
        report.setStatusId(statusId);
        report.setLastUpdate(lastUpdate);
        report.setTypeId(typeId);

        reportString = "Report{" +
                "id=" + id +
                ", date=" + date +
                ", lastUpdate=" + date +
                ", statusId=" + statusId +
                ", typeId=" + typeId + '\'' +
                '}';
        assertEquals(reportString, report.toString());
    }
}