package com.my.kramarenko.taxService.web.command;

import com.my.kramarenko.taxService.web.command.common.*;
import com.my.kramarenko.taxService.xml.ReportForm;
import com.my.kramarenko.taxService.xml.forms.F0103405;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ReportFormContainer {

    private static final Logger LOG = Logger.getLogger(ReportFormContainer.class);

    private static final Map<String, ReportForm> commands = new HashMap<>();

    static {
        // common commands
        commands.put("F0103405", new F0103405());
        LOG.trace("Report form container was successfully initialized");
        LOG.trace("Number of forms --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param formId id of the form.
     * @return Command object.
     */
    public static ReportForm getForm(String formId) {
        if (formId == null || !commands.containsKey(formId)) {
            LOG.debug("form not found, id --> " + formId);
            return commands.get("noCommand");
        }
        LOG.debug("Obtained form: " + formId);
        return commands.get(formId);
    }
}