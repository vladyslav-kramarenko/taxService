package com.my.kramarenko.taxService.web.command;

import com.my.kramarenko.taxService.web.command.admin.AllUsersCommand;
import com.my.kramarenko.taxService.web.command.admin.DeleteUserCommand;
import com.my.kramarenko.taxService.web.command.admin.EditUserCommand;
import com.my.kramarenko.taxService.web.command.common.*;
import com.my.kramarenko.taxService.web.command.inspector.ReportStatisticCommand;
import com.my.kramarenko.taxService.web.command.inspector.UserStatisticCommand;
import com.my.kramarenko.taxService.web.command.inspector.UpdateReportStatus;
import com.my.kramarenko.taxService.web.command.outOfControl.LoginCommand;
import com.my.kramarenko.taxService.web.command.outOfControl.RegistrationCommand;
import com.my.kramarenko.taxService.web.command.outOfControl.ResetPasswordCommand;
import com.my.kramarenko.taxService.web.command.outOfControl.UpdateLocaleCommand;
import com.my.kramarenko.taxService.web.command.user.*;
import com.my.kramarenko.taxService.web.command.common.ReportCommand;
import com.my.kramarenko.taxService.web.command.common.ReportListCommand;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

    private static final Logger LOG = Logger.getLogger(CommandContainer.class);

    private static final Map<String, Command> commands = new HashMap<>();

    static {
        // common commands
        commands.put("login", new LoginCommand());
        commands.put("info", new InfoCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("noCommand", new NoCommand());
        commands.put("updateLocale", new UpdateLocaleCommand());
        commands.put("downloadReport", new PdfDownloadCommand());
        commands.put("resetPassword", new ResetPasswordCommand());
        commands.put("viewReport", new ShowReportCommand());

//        commands.put("complete", new CompleteCommand());
        commands.put("viewSettings", new ViewSettingsCommand());
        commands.put("downloadXML", new DownloadReportAsXmlCommand());

        // user commands
        commands.put("loadXML", new LoadXMLCommand());
        commands.put("submitReport", new SubmitReportCommand());
        commands.put("saveReport", new SubmitReportCommand());

        commands.put("deleteReport", new DeleteReportCommand());
        commands.put("reportList", new ReportListCommand());
        commands.put("cancelReport", new CancelReportCommand());

        // inspector commands
        commands.put("updateReportStatus", new UpdateReportStatus());
        commands.put("statistic", new UserStatisticCommand());
        commands.put("reportStatistic", new ReportStatisticCommand());

        //user & inspector commands
        commands.put("editReport", new ReportCommand());

        // admin commands
        commands.put("changeUserBannedStatus", new AllUsersCommand());
        commands.put("allUsers", new AllUsersCommand());
        commands.put("changeUserRole", new AllUsersCommand());
        commands.put("editUser", new EditUserCommand());
        commands.put("deleteUser", new DeleteUserCommand());

        LOG.trace("Command container was successfully initialized");
        LOG.trace("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */
    public static Command getCommand(String commandName) {
        if (commandName == null || commandName.isEmpty()) {
            LOG.debug("command name is empty");
            return commands.get("noCommand");
        }
        commandName = commandName.trim();
        LOG.trace("Looking for a command [" + commandName + "]");
        if (!commands.containsKey(commandName)) {
            LOG.debug("Command not found, name --> [" + commandName + "]");
            return commands.get("noCommand");
        }
        LOG.debug("Obtained command: " + commandName);
        return commands.get(commandName);
    }
}