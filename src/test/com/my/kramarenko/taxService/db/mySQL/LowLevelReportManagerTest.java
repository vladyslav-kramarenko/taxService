package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
            Report report = new Report();
            report.setTypeId("1");
            report.setStatusId(1);
            ReportManager.addReport(con, report);
            Optional<User> user = UserManager.getUser(con, 1);
            ReportManager.setUserReport(con, user.get(), report);
            List<Report> reports = ReportManager.getAllReports(con);
            assertTrue(reports.size() > 0);

            Report report1 = reports.get(0);
            Report report2 = ReportManager.getReport(con, report1.getId());
            assertEquals(report1, report2);

            ReportManager.deleteReport(con, report.getId());
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