package com.my.kramarenko.taxService.web.command.common;

import com.my.kramarenko.taxService.Util;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.exception.XmlException;
import com.my.kramarenko.taxService.db.dto.ReportDTO;
import com.my.kramarenko.taxService.db.dto.UserReportDTOBuilder;
import com.my.kramarenko.taxService.db.entity.Type;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.db.dao.TypeDAO;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.web.command.util.UserUtil;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serial;
import java.util.*;

import static com.my.kramarenko.taxService.web.command.util.ReportUtil.getChosenStatusMap;

/**
 * ReportList command.
 *
 * @author Vlad Kramarenko
 */
public class ReportListCommand extends Command {

    @Serial
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

        String typeFilter = request.getParameter("typeFilter");
        request.setAttribute("typeFilter", typeFilter);

        if (roleMap.get(user.getRoleId()).equals(Role.USER)) {
            LOG.trace("user role == user -> go to user reports");
//            reportsList = UserReportDTOBuilder.getUserReportsWithStatuses(user, chosenStatusMap, typeMap);
            reportsList = UserReportDTOBuilder.getUserReportsWithStatusesWithTypeFilter(chosenStatusMap, typeMap, user, typeFilter);
            TypeDAO tm = DBManager.getInstance().getTypeDAO();
            reportTypes = tm.getAllTypes();
            request.setAttribute("reportTypeList", reportTypes);
        } else {
            List<User> users = UserUtil.getUserListDependOnFilter(request);
            reportsList = UserReportDTOBuilder.getUsersReportsWithStatusesAndTypeFilter(chosenStatusMap, typeMap, users, typeFilter);
//            if (roleMap.get(user.getRoleId()).equals(Role.ADMIN)) {
//            }
        }

        request.setAttribute("chosenStatusMap", chosenStatusMap);

        Util.setReportsWithPagination(reportsList, request);
        request.getSession().setAttribute("page", Path.COMMAND_REPORT_LIST);
//        request.getSession().setAttribute("page", forward);
        LOG.trace("Command finished");
        return forward;
    }
}