package com.my.kramarenko.taxService.web.command.common;

import com.my.kramarenko.taxService.Util;
import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.XmlException;
import com.my.kramarenko.taxService.db.dto.ReportDTO;
import com.my.kramarenko.taxService.db.dao.TypeDao;
import com.my.kramarenko.taxService.db.dto.UserReportDTOBuilder;
import com.my.kramarenko.taxService.db.entity.Type;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.db.entity.Status;
import com.my.kramarenko.taxService.db.mySQL.TypeManager;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.Command;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ReportList command.
 *
 * @author Vlad Kramarenko
 */
public class ReportListCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(ReportListCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, DBException, XmlException {

        LOG.trace("Command starts");
        String forward = Path.PAGE_USER_REPORT_LIST;

        User user = (User) request.getSession().getAttribute("user");

        List<ReportDTO> reportsList;
        String[] chosenIdParameter = request.getParameterValues("chosen_status_id");

        ServletContext sc = request.getServletContext();
        Map<Integer, Status> statusMap = (Map<Integer, Status>) sc.getAttribute("statusMap");
        Map<Integer, Role> roleMap = (Map<Integer, Role>) sc.getAttribute("roleMap");
        Map<String, Type> typeMap = (Map<String, Type>) sc.getAttribute("typeMap");

        Map<Status, Boolean> chosenStatusMap = getChosenStatusMap(statusMap, chosenIdParameter);

        List<Type> reportTypes;

        if (roleMap.get(user.getRoleId()).equals(Role.USER)) {
            LOG.trace("user role == user -> go to user reports");
            reportsList = UserReportDTOBuilder.getAllUserReportsWithStatuses(user, chosenStatusMap, typeMap);
            TypeDao tm = new TypeManager();
            reportTypes = tm.getAllTypes();
            request.setAttribute("reportTypeList", reportTypes);
        } else {
//            if (roleMap.get(user.getRoleId()).equals(Role.ADMIN)) {
//            }
            reportsList = UserReportDTOBuilder.getAllReportsWithStatuses(chosenStatusMap, typeMap);
        }

        request.setAttribute("chosenStatusMap", chosenStatusMap);

        Util.setReportsWithPagination(reportsList, request);
        request.getSession().setAttribute("page", forward);
        LOG.trace("Command finished");
        return forward;
    }

    private static Map<Status, Boolean> getChosenStatusMap(Map<Integer, Status> statusMap, String[] chosedId) {
        Map<Status, Boolean> chosenStatuses;
        if (chosedId != null) {
            chosenStatuses = statusMap.values().stream().collect(Collectors.toMap(x -> x, x -> false));
            for (String s : chosedId) {
                int id = Integer.parseInt(s);
                chosenStatuses.put(statusMap.get(id), true);
            }
        } else {
            chosenStatuses = statusMap.values().stream().collect(Collectors.toMap(x -> x, x -> true));
        }
        return chosenStatuses;
    }
}