package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.entity.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LowLevelReportManagerTest {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/taxservicedb?user=user&password=user";
    private static Connection con;

    @BeforeEach
    void setUp() throws SQLException {
        con = DriverManager.getConnection(CONNECTION_URL);
    }

//    @Test
//    void getUserReports() throws SQLException {
//        User user = LowLevelUserManager.getAllUsers(con).get(0);
//        Report report=new Report();
//        report.setTypeId();
//        LowLevelReportManager.getUserReports(con, )
//    }

    @Test
    void getReportTests() throws SQLException {
        try {
            List<Report> reports = LowLevelReportManager.getAllReports(con);
            assertTrue(reports.size() > 0);

            Report report1 = reports.get(0);
            Report report2 = LowLevelReportManager.getReport(con, report1.getId());
            assertEquals(report1, report2);
        } finally {
            con.close();
        }
    }

//    @Test
//    void getUserReportsWithStatuses() {
//    }
//
//    @Test
//    void addReport() {
//    }
//
//    @Test
//    void setReportStatusAndXmlPath() {
//    }
//
//    @Test
//    void setUserReport() {
//    }
}