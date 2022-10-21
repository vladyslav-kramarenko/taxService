package com.my.kramarenko.taxService.db.dto;

import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.Type;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportDTOTest {

    @Test
    void reportDTOTests() {
        ReportDTO reportDTO=new ReportDTO();
        Report report=new Report();
        reportDTO.setReport(report);
        assertEquals(report, reportDTO.getReport());

        User user=new User();
        user.setId(0);
        user.setEmail("email");
        reportDTO.setUser(user);
        assertEquals(user,reportDTO.getUser());

        Status status = Status.DRAFT;
        reportDTO.setStatus(status);
        assertEquals(status,reportDTO.getStatus());

        Type type=new Type();
        type.setId("0");
        type.setName("name");
        reportDTO.setType(type);
        assertEquals(type,reportDTO.getType());

        String reportDTOString="ReportDTO{" +
                "user=" + user +
                ", report=" + report +
                ", type=" + type +
                ", status=" + status +
                '}';
        assertEquals(reportDTOString,reportDTO.toString());
    }
}