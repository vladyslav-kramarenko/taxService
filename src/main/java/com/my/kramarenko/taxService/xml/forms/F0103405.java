package com.my.kramarenko.taxService.xml.forms;

import com.my.kramarenko.taxService.xml.entity.ReportValue;

public class F0103405 extends ReportForm {
    {
        addHeadValue(new ReportValue("TIN", "Long"));
        addHeadValue(new ReportValue("C_DOC", "String"));
        addHeadValue(new ReportValue("C_DOC_SUB", "String"));
        addHeadValue(new ReportValue("C_DOC_VER", "String"));
        addHeadValue(new ReportValue("C_DOC_TYPE", "Int"));
        addHeadValue(new ReportValue("C_DOC_CNT", "Int"));
        addHeadValue(new ReportValue("C_REG", "Int"));
        addHeadValue(new ReportValue("C_RAJ", "Int"));
        addHeadValue(new ReportValue("PERIOD_MONTH", "Int"));
        addHeadValue(new ReportValue("PERIOD_TYPE", "Int"));
        addHeadValue(new ReportValue("PERIOD_YEAR", "Int"));
        addHeadValue(new ReportValue("C_STI_ORIG", "Int"));
        addHeadValue(new ReportValue("C_DOC_STAN", "Int"));
        addHeadValue(new ReportValue("LINKED_DOCS", "LinkedDoc"));
        addHeadValue(new ReportValue("D_FILL", "Int"));
        addHeadValue(new ReportValue("SOFTWARE", "String"));

        addBodyValue(new ReportValue("HTIN", "Long"));
        addBodyValue(new ReportValue("HNAME", "String"));
        addBodyValue(new ReportValue("HF", "String"));
        addBodyValue(new ReportValue("HLOC", "String"));
        addBodyValue(new ReportValue("HTEL", "String"));
        addBodyValue(new ReportValue("HKSTI", "Int"));
        addBodyValue(new ReportValue("HKBOS", "Int"));
        addBodyValue(new ReportValue("HBOS", "String"));
        addBodyValue(new ReportValue("HKVED", "String"));
        addBodyValue(new ReportValue("HKEXECUTOR", "String"));
        addBodyValue(new ReportValue("HZIP", "Int"));
        addBodyValue(new ReportValue("HREG", "String"));
        addBodyValue(new ReportValue("HRAJ", "String"));
        addBodyValue(new ReportValue("HCITY", "String"));
        addBodyValue(new ReportValue("HSTREET", "String"));
        addBodyValue(new ReportValue("HBUILD", "String"));
        addBodyValue(new ReportValue("HCORP", "String"));
        addBodyValue(new ReportValue("HAPT", "String"));
        addBodyValue(new ReportValue("HLNAME", "String"));
        addBodyValue(new ReportValue("HPNAME", "String"));
        addBodyValue(new ReportValue("HFNAME", "String"));
        addBodyValue(new ReportValue("HCOUNTRY", "String"));
        addBodyValue(new ReportValue("HEMAIL", "String"));
        addBodyValue(new ReportValue("HFILL", "String"));
        addBodyValue(new ReportValue("HZ", "String"));
        addBodyValue(new ReportValue("HY", "String"));
        addBodyValue(new ReportValue("HZY", "Int"));
        addBodyValue(new ReportValue("HSTI", "String"));
//        I. ЗАГАЛЬНІ ПОКАЗНИКИ ПІДПРИЄМНИЦЬКОЇ ДІЯЛЬНОСТІ
        addBodyValue(new ReportValue("HNACTL", "Int"));
//        ІІ. ПОКАЗНИКИ ГОСПОДАРСЬКОЇ ДІЯЛЬНОСТІ ДЛЯ ПЛАТНИКІВ ЄДИНОГО ПОДАТКУ ПЕРШОЇ ГРУПИ
        addBodyValue(new ReportValue("R02G1", "Double"));
        addBodyValue(new ReportValue("R02G2", "Double"));
        addBodyValue(new ReportValue("R02G3", "Double"));
        addBodyValue(new ReportValue("R02G4", "Double"));
        addBodyValue(new ReportValue("R001G3", "Double"));
        addBodyValue(new ReportValue("R002G3", "Double"));
//ІІІ. ПОКАЗНИКИ ГОСПОДАРСЬКОЇ ДІЯЛЬНОСТІ ДЛЯ ПЛАТНИКІВ ЄДИНОГО ПОДАТКУ ДРУГОЇ ГРУПИ
        addBodyValue(new ReportValue("R03G1", "Double"));
        addBodyValue(new ReportValue("R03G2", "Double"));
        addBodyValue(new ReportValue("R03G3", "Double"));
        addBodyValue(new ReportValue("R03G4", "Double"));
        addBodyValue(new ReportValue("R003G3", "Double"));
        addBodyValue(new ReportValue("R004G3", "Double"));
//        ІV. ПОКАЗНИКИ ГОСПОДАРСЬКОЇ ДІЯЛЬНОСТІ ДЛЯ ПЛАТНИКІВ ЄДИНОГО ПОДАТКУ ТРЕТЬОЇ ГРУПИ
        addBodyValue(new ReportValue("R005G3", "Double"));
        addBodyValue(new ReportValue("R006G3", "Double"));
        addBodyValue(new ReportValue("R007G3", "Double"));
//        V. ВИЗНАЧЕННЯ ПОДАТКОВИХ ЗОБОВ'ЯЗАНЬ ПО ЄДИНОМУ ПОДАТКУ
        addBodyValue(new ReportValue("R008G3", "Double"));
        addBodyValue(new ReportValue("R009G3", "Double"));
        addBodyValue(new ReportValue("R010G3", "Double"));
        addBodyValue(new ReportValue("R011G3", "Double"));
        addBodyValue(new ReportValue("R012G3", "Double"));
        addBodyValue(new ReportValue("R013G3", "Double"));
        addBodyValue(new ReportValue("R014G3", "Double"));
    }

    public F0103405() {
        setReportType("F0103405");
    }
}

