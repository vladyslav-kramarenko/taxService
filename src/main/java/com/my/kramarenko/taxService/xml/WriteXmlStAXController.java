package com.my.kramarenko.taxService.xml;

import com.my.kramarenko.taxService.db.mySQL.UserManager;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WriteXmlStAXController {

    private static final Logger LOG = Logger.getLogger(WriteXmlStAXController.class);

    public void writeToXML(TaxForm taxForm, ReportForm reportForm, String fileName) throws XMLStreamException, IOException {
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        FileOutputStream out = null;
        XMLStreamWriter writer = null;
        try {
            //TODO create directory if not created
            //default tomcat/bin
            out = new FileOutputStream(fileName);
            writer = output.createXMLStreamWriter(out);

            writer.writeStartDocument("windows-1251", "1.0");
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

        } catch (XMLStreamException | IOException e) {
            LOG.error(e.getMessage());
        } finally {
            writer.flush();
            writer.close();
            out.close();
        }

    }

    private void writeLinkedDocs(XMLStreamWriter writer, List<LinkedDoc> entry) throws XMLStreamException {
        writer.writeStartElement("LINKED_DOCS");
        for (int i = 0; i < entry.size(); i++) {
            writer.writeStartElement("DOC");

            LinkedDoc doc = entry.get(i);
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
