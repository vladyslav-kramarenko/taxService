package com.my.kramarenko.taxService.xml.forms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportFormContainerTest {

    @Test
    void getForm() {
        assertEquals(new F0103405(), ReportFormContainer.getForm("F0103405"));
        assertEquals(new F0134105(), ReportFormContainer.getForm("F0134105"));
    }
}