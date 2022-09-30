package com.my.kramarenko.taxService.xml.taxDeclFop;

import java.util.Map;

public class F0103405 implements ReportForm {
//    Map<String, String> head = new TreeMap<>();
//    Map<String, String> body = new TreeMap<>();

    {
        head.put("TIN","Long");
        head.put("C_DOC","String");
        head.put("C_DOC_SUB","String");
        head.put("C_DOC_VER","String");
        head.put("C_DOC_TYPE","Int");
        head.put("C_DOC_CNT","Int");
        head.put("C_REG","Int");
        head.put("C_RAJ","Int");
        head.put("PERIOD_MONTH","Int");
        head.put("PERIOD_TYPE","Int");
        head.put("PERIOD_YEAR","Int");
        head.put("C_DOC_STAN","Int");
        head.put("LINKED_DOC","LinkedDoc" );
        head.put("D_FILL","Int");
        head.put("SOFTWARE","String");

        body.put("HTIN","Long");
        body.put("HNAME","String");
        body.put("HF","String");
        body.put("HLOC","String");
        body.put("HTEL","String");
        body.put("HKSTI","Int");
        body.put("HKBOS","Int");
        body.put("HBOS","String");
        body.put("HKVED","String");
        body.put("HKEXECUTOR","String");
        body.put("HZIP","Int");
        body.put("HREG","String");
        body.put("HRAJ","String");
        body.put("HCITY","String");
        body.put("HSTREET","String");
        body.put("HBUILD","String");
        body.put("HCORP","String");
        body.put("HAPT","String");
        body.put("HLNAME","String");
        body.put("HPNAME","String");
        body.put("HFNAME","String");
        body.put("HCOUNTRY","String");
        body.put("HEMAIL","String");
        body.put("HFILL","String");
        body.put("HZ","String");
        body.put("HY","String");
        body.put("HZY","Int");
        body.put("HSTI","String");
//        I. ЗАГАЛЬНІ ПОКАЗНИКИ ПІДПРИЄМНИЦЬКОЇ ДІЯЛЬНОСТІ
        body.put("HNACTL","Int");
//        ІІ. ПОКАЗНИКИ ГОСПОДАРСЬКОЇ ДІЯЛЬНОСТІ ДЛЯ ПЛАТНИКІВ ЄДИНОГО ПОДАТКУ ПЕРШОЇ ГРУПИ
        body.put("R02G1","Double");
        body.put("R02G2","Double");
        body.put("R02G3","Double");
        body.put("R02G4","Double");
        body.put("R001G3","Double");
        body.put("R002G3","Double");
//ІІІ. ПОКАЗНИКИ ГОСПОДАРСЬКОЇ ДІЯЛЬНОСТІ ДЛЯ ПЛАТНИКІВ ЄДИНОГО ПОДАТКУ ДРУГОЇ ГРУПИ
        body.put("R03G1","Double");
        body.put("R03G2","Double");
        body.put("R03G3","Double");
        body.put("R03G4","Double");
        body.put("R003G3","Double");
        body.put("R004G3","Double");
//        ІV. ПОКАЗНИКИ ГОСПОДАРСЬКОЇ ДІЯЛЬНОСТІ ДЛЯ ПЛАТНИКІВ ЄДИНОГО ПОДАТКУ ТРЕТЬОЇ ГРУПИ
        body.put("R005G3","Double");
        body.put("R006G3","Double");
        body.put("R007G3","Double");
//        V. ВИЗНАЧЕННЯ ПОДАТКОВИХ ЗОБОВ'ЯЗАНЬ ПО ЄДИНОМУ ПОДАТКУ
        body.put("R008G3","Double");
        body.put("R009G3","Double");
        body.put("R010G3","Double");
        body.put("R011G3","Double");
        body.put("R012G3","Double");
        body.put("R013G3","Double");
        body.put("R014G3","Double");
    }

    public Map<String, String> getHead() {
        return head;
    }

    public Map<String, String> getBody() {
        return body;
    }

}

