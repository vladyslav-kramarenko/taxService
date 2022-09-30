package com.my.kramarenko.taxService.db.dao;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.entity.Report;

import java.util.List;

public interface ReportTypeDao {
    List<Report> getAllReportTypes() throws DBException;
}
