package com.my.kramarenko.taxService.db.dto;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.Type;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportDTOBuilder {
    private static final Logger LOG = Logger.getLogger(ReportDTOBuilder.class);

    public static ReportDTO getReportDTO(Report report,Map<String, Type>typeMap) throws DBException {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setReport(report);
        reportDTO.setStatus(DBManager.getInstance().getStatusManager().getStatus(report.getStatusId()));
        reportDTO.setType(typeMap.get(report.getTypeId()));

        return reportDTO;
    }

    public static List<ReportDTO> getReportsDTO(List<Report> reports,Map<String, Type>typeMap) throws DBException {
        List<ReportDTO> reportsDTO = new ArrayList<>();
        for (Report report : reports) {
            reportsDTO.add(getReportDTO(report, typeMap));
        }
        return reportsDTO;
    }
}
