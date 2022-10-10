package com.my.kramarenko.taxService.xml;

import java.util.*;

public class SubElement {
    private Map<String, List> parameters;

    public SubElement() {
        parameters = new TreeMap<>();
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

    public Map<String, List> getParameters() {
        return parameters;
    }

    public boolean containsKey(String key){
        return parameters.containsKey(key);
    }

    public Set<Map.Entry<String, List>> getEntrySet() {
        return parameters.entrySet();
    }

}
