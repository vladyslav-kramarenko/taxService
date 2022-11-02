package com.my.kramarenko.taxService.web.mail;

import com.my.kramarenko.taxService.db.PasswordCreator;
import com.my.kramarenko.taxService.db.entity.Report;
import com.my.kramarenko.taxService.db.entity.Type;
import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.enums.Status;
import com.my.kramarenko.taxService.db.mySQL.DBManager;
import com.my.kramarenko.taxService.exception.DBException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import java.util.Map;

public class MailCreator {
    private static final Logger LOG = Logger.getLogger(MailCreator.class);

    public static void createReportUpdateNotification(HttpServletRequest request, int reportID) {
        LOG.trace("Command starts");
        try {
            Report report = DBManager.getInstance().getReportDAO().getReport(reportID);
            ServletContext sc = request.getServletContext();
            Map<String, Type> typeMap = (Map<String, Type>) sc.getAttribute("typeMap");

            User user = DBManager.getInstance().getUserDAO().getUserByReportId(reportID).get();

            String reportType = typeMap.get(report.getTypeId()).getName();
            String reportStatus = Status.getStatus(report.getStatusId()).getName();
            String updateTime = report.getLastUpdate().toLocalDateTime().toString();
            MailCreator.sentReportUpdateStatus(reportType, reportStatus, updateTime, user.getEmail());
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
        }
        LOG.trace("Command finished");
    }

    public static void sentReportUpdateStatus(String reportTypeName, String reportStatus, String date, String email) {
        LOG.trace("Command starts");
        String message = "Your report '" + reportTypeName + "' was updated " + date + "\n Current report status: " + reportStatus;
        String subject = "Update for your report";
        LOG.trace("email to " + email + ":\n" + subject + "\n" + message);
        try {
            MailHelper.sendMail(email, subject, message);
        } catch (Throwable e) {
            LOG.error(e.getMessage(), e);
        }
        LOG.trace("Command finished");
    }

    public static void resetPassword(String email, User user) throws ServletException {
        LOG.trace("Command starts");
        String password = PasswordCreator.generatePassword();
        String oldPassword = user.getPassword();
        try {
            user.setPassword(PasswordCreator.getPassword(password));
            DBManager.getInstance().getUserDAO().updateUser(user);
        } catch (Exception e) {
            throw new ServletException("Can't reset password", e);
        }
        try {
            String message = "Hi, this is your new password: "
                    + password
                    + "\nNote: for security reason, "
                    + "you must change your password after logging in.";
            String subject = "Your Password has been reset";
            MailHelper.sendMail(email, subject, message);
        } catch (Exception e) {
            LOG.trace(e.getMessage());
            try {
                user.setPassword(oldPassword);
                DBManager.getInstance().getUserDAO().updateUser(user);
            } catch (DBException ex) {
                LOG.error(ex.getMessage());
            }
            throw new ServletException("Can't reset password", e);
        }
        LOG.trace("Command finished");
    }
}
