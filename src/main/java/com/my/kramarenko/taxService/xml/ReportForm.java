package com.my.kramarenko.taxService.xml.taxDeclFop;

import java.util.Map;
import java.util.TreeMap;

public interface ReportForm {
    Map<String, String> head = new TreeMap<>();
    Map<String, String> body = new TreeMap<>();
}
