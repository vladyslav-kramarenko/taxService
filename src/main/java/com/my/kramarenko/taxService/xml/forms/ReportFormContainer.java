package com.my.kramarenko.taxService.xml.forms;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ReportFormContainer {

    private static final Logger LOG = Logger.getLogger(ReportFormContainer.class);

    private static final Map<String, ReportForm> commands = new HashMap<>();

    static {
        // individual
        commands.put("F0103405", new F0103405());
        commands.put("F0134105", new F0134105());
        // organization
        commands.put("J0100122", new J0100122());


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