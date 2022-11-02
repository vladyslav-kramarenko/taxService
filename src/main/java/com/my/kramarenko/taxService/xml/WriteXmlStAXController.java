package com.my.kramarenko.taxService.xml;

import com.my.kramarenko.taxService.xml.entity.LinkedDoc;
import com.my.kramarenko.taxService.xml.forms.ReportForm;
import com.my.kramarenko.taxService.xml.entity.ReportValue;
import com.my.kramarenko.taxService.xml.entity.TaxForm;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class WriteXmlStAXController {
    private final String ENCODING = "windows-1251";
    private static final Logger LOG = Logger.getLogger(WriteXmlStAXController.class);

    public void writeToXML(TaxForm taxForm, ReportForm reportForm, String fileName) throws XMLStreamException, IOException {
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            //TODO create directory if not created
            //default tomcat/bin
            writeToFileOutputStream(out, taxForm, reportForm);
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
            LOG.error(e.getMessage(),e);
            throw new XMLStreamException("Can't write the XML", e);
        }
    }

    public void writeToFileOutputStream(FileOutputStream out, TaxForm taxForm, ReportForm reportForm) throws XMLStreamException {
        XMLStreamWriter writer = null;
        try {
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            writer = output.createXMLStreamWriter(out, ENCODING);
            writeToXmlStream(writer, taxForm, reportForm);
        } finally {
            writer.flush();
            writer.close();
        }
    }


    public String writeToString(TaxForm taxForm, ReportForm reportForm) throws XMLStreamException {
        XMLStreamWriter writer = null;
        StringWriter stringOut = new StringWriter();
        try {
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            writer = output.createXMLStreamWriter(stringOut);
            writeToXmlStream(writer, taxForm, reportForm);
        } finally {
            writer.flush();
            writer.close();
        }

        String output = stringOut.toString();
        LOG.trace(output);
        return output;
    }

    public void writeToFileOutputStream(BufferedOutputStream out, TaxForm taxForm, ReportForm reportForm) throws XMLStreamException {
        XMLStreamWriter writer = null;
        try {
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            writer = output.createXMLStreamWriter(out, ENCODING);
            writeToXmlStream(writer, taxForm, reportForm);
        } finally {
            writer.flush();
            writer.close();
        }
    }


    private void writeToXmlStream(XMLStreamWriter writer, TaxForm taxForm, ReportForm reportForm) throws XMLStreamException {
        writer.writeStartDocument(ENCODING, "1.0");
        writer.writeStartElement("DECLAR");
        writer.writeAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        writer.writeAttribute(" xsi:noNamespaceSchemaLocation", "F0103405.xsd");

        writer.writeStartElement("DECLARHEAD");
        for (ReportValue entry : reportForm.getHead()) {
            if (entry.name.equals("LINKED_DOCS") && taxForm.getDeclarHead().containsKey("LINKED_DOCS")) {
                writeLinkedDocs(writer, taxForm.getDeclarHead().getParameter("LINKED_DOCS"));
            } else {
                if (taxForm.getDeclarHead().containsKey(entry.name)) {
                    writeElement(writer, entry.name, taxForm.getDeclarHead().getParameter(entry.name));
                }
            }
        }
        writer.writeEndElement();//DECLARHEAD

        writer.writeStartElement("DECLARBODY");
        for (ReportValue entry : reportForm.getBody()) {
            if (taxForm.getDeclarBody().containsKey(entry.name)) {
                writeElement(writer, entry.name, taxForm.getDeclarBody().getParameter(entry.name));
            }
        }
        writer.writeEndElement();//DECLARBODY
        writer.writeEndElement();//DECLAR
    }

    private void writeLinkedDocs(XMLStreamWriter writer, List<LinkedDoc> entry) throws XMLStreamException {
        writer.writeStartElement("LINKED_DOCS");
        for (LinkedDoc linkedDoc : entry) {
            writer.writeStartElement("DOC");

            LinkedDoc doc = linkedDoc;
            writer.writeAttribute("NUM", doc.getNum());
            writer.writeAttribute("TYPE", doc.getDocType());

            writer.writeStartElement("C_DOC");
            writer.writeCharacters(doc.getDoc());
            writer.writeEndElement();//C_DOC

            writer.writeStartElement("C_DOC_SUB");
            writer.writeCharacters(doc.getDocSub());
            writer.writeEndElement();//C_DOC_SUB

            writer.writeStartElement("C_DOC_VER");
            writer.writeCharacters(doc.getDocVer());
            writer.writeEndElement();//C_DOC_VER

            writer.writeStartElement("C_DOC_TYPE");
            writer.writeCharacters(doc.getDocType());
            writer.writeEndElement();//C_DOC_TYPE

            writer.writeStartElement("C_DOC_CNT");
            writer.writeCharacters(doc.getDocCnt());
            writer.writeEndElement();//C_DOC_CNT

            writer.writeStartElement("C_DOC_STAN");
            writer.writeCharacters(doc.getDocStan());
            writer.writeEndElement();//C_DOC_STAN

            writer.writeStartElement("FILENAME");
            writer.writeCharacters(doc.getFilename());
            writer.writeEndElement();//FILENAME

            writer.writeEndElement();//DOC
        }
        writer.writeEndElement();//LINKED_DOCS
    }

    private void writeElement(XMLStreamWriter writer, String name, List entry) throws XMLStreamException {
        writer.writeStartElement(name);
        writer.writeCharacters(entry.get(0).toString());
        System.out.println("write element [" + name + "]");
        writer.writeEndElement();
    }
}
