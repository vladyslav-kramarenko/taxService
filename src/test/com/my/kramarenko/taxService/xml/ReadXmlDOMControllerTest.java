package com.my.kramarenko.taxService.xml;

import com.my.kramarenko.taxService.exception.XmlException;
import com.my.kramarenko.taxService.xml.forms.ReportForm;
import com.my.kramarenko.taxService.xml.entity.TaxForm;
import com.my.kramarenko.taxService.xml.forms.ReportFormContainer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReadXmlDOMControllerTest {

    @Test
    void loadFileF0103405() throws XmlException {
        String xmlFileName = "zvit_main.XML";
        ReadXmlDOMController domController = new ReadXmlDOMController();
        TaxForm taxForm = null;
        ReportForm reportForm = ReportFormContainer.getForm("F0103405");
        try {
            taxForm = domController.loadFile(reportForm, xmlFileName);
        } catch (Exception e) {
            e.printStackTrace();

        }
        assertEquals(3362103457L, taxForm.getDeclarHead().getParameter("TIN").get(0));
    }

    @Test
    void loadFileF0134105() throws XmlException {
        String xmlFileName = "zvit_add.XML";
        ReadXmlDOMController domController = new ReadXmlDOMController();
        TaxForm taxForm = null;
        ReportForm reportForm = ReportFormContainer.getForm("F0134105");
        try {
            taxForm = domController.loadFile(reportForm, xmlFileName);
        } catch (Exception e) {
            e.printStackTrace();

        }
        assertEquals(3362103457L, taxForm.getDeclarHead().getParameter("TIN").get(0));
    }

    @Test
    void loadInputStream() {
    }
}