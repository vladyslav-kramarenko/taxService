package com.my.kramarenko.taxService.web.command.user;

import com.my.kramarenko.taxService.db.DBException;
import com.my.kramarenko.taxService.db.XmlException;
import com.my.kramarenko.taxService.db.bean.ReportBean;
import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.db.mySQL.ReportBeanManager;
import com.my.kramarenko.taxService.db.mySQL.ReportManager;
import com.my.kramarenko.taxService.web.Path;
import com.my.kramarenko.taxService.web.command.Command;
import com.my.kramarenko.taxService.xml.ReadXmlDOMController;
import com.my.kramarenko.taxService.xml.ReportForm;
import com.my.kramarenko.taxService.xml.TaxForm;
import com.my.kramarenko.taxService.xml.forms.F0103405;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Login command.
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

        String forward = Path.PAGE_REPORT_LIST;

        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            ReportBeanManager reportBeanManager = new ReportBeanManager();
            List<ReportBean> reportsList = new LinkedList<>();
            if(Role.getRole(user).equals(Role.INSPECTOR)){
                reportsList = reportBeanManager.getAllReportList();
            }
            else{
                reportsList = reportBeanManager.getUserReportList(user);
            }
            request.setAttribute("reportsList", reportsList);
        }

        LOG.trace("Command finished");
        return forward;
    }
}