package com.my.kramarenko.taxService.web.command.inspector;

import com.my.kramarenko.taxService.db.dto.StatisticDTO;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.exception.CommandException;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.Util;
import com.my.kramarenko.taxService.web.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.List;

public class ReportStatisticCommand extends Command {
    private static final Logger LOG = Logger.getLogger(ReportStatisticCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        List<StatisticDTO> statistics;
        try {
            Util.updateLastPageIfEmpty(request,Path.COMMAND_REPORT_STATISTIC);
            String typeFilter = request.getParameter("typeFilter");
            request.setAttribute("typeFilter", typeFilter);

            statistics = DBManager.getInstance().getReportDAO().getFilterReportStatistics(typeFilter);

            Util.setReportsWithPagination(statistics, request);
        } catch (DBException e) {
            LOG.error(e.getMessage(),e);
            throw new CommandException("Can't load reports statistic", e);
        }
        return Path.PAGE_REPORT_STATISTIC;
    }
}
