package com.my.kramarenko.taxService.xml.taxDeclFop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SubElement {
    private Map<String, List> parameters;

    public SubElement() {
        parameters = new HashMap<>();
    }

    public List getParameter(String parameterName) {
        return parameters.get(parameterName);
    }

    public void addParameter(String parameterName, List values) {
        parameters.put(parameterName, values);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Paramaters {\n");
        parameters.entrySet().forEach(x -> result
                .append(x.getKey())
                .append(": ")
                .append(x.getValue())
                .append("\n"));
        result.append("}");
        return result.toString();
    }

    public Set<Map.Entry<String, List>> getEntrySet() {
        return parameters.entrySet();
    }
}
