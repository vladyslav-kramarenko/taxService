package com.my.kramarenko.taxService.xml;

import com.my.kramarenko.taxService.db.XmlException;
import com.my.kramarenko.taxService.xml.entity.TaxForm;
import com.my.kramarenko.taxService.xml.forms.ReportForm;
import com.my.kramarenko.taxService.xml.forms.ReportFormContainer;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WriteXmlStAXControllerTest {
    @Test
    void readAndLoadFileF0103405() throws XmlException, XMLStreamException, IOException {
        String xmlFileName = "zvit_main.XML";
        ReadXmlDOMController domController = new ReadXmlDOMController();

        ReportForm reportForm = ReportFormContainer.getForm("F0103405");
        TaxForm taxForm = domController.loadFile(reportForm, xmlFileName);

        WriteXmlStAXController write = new WriteXmlStAXController();
        String newFile = "test.xml";
        write.writeToXML(taxForm, reportForm, newFile);

        TaxForm taxForm1 = domController.loadFile(reportForm, newFile);

        assertEquals(taxForm.getDeclarHead().getParameter("TIN").get(0), taxForm1.getDeclarHead().getParameter("TIN").get(0));
    }
}