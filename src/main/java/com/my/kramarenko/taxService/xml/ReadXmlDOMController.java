package com.my.kramarenko.taxService.xml;

import com.my.kramarenko.taxService.db.XmlException;
import com.my.kramarenko.taxService.xml.forms.F0103405;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for DOM parser.
 */
public class ReadXmlDOMController {
    private static final Logger LOG = Logger.getLogger(ReadXmlDOMController.class);
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;

    public ReadXmlDOMController() throws XmlException {

        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new XmlException("Can't create a documentBuilder",e);
        }
    }

    public TaxForm loadFile(ReportForm reportForm, String xmlFileName) throws XmlException {
        try {
            LOG.trace("Try to load file: "+xmlFileName);
            File inputFile = new File(xmlFileName);

            Document doc = documentBuilder.parse(inputFile);
            return load(reportForm, doc);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            LOG.error(e.getMessage());
            throw new XmlException("Can't open this XML file", e);
        }
    }

    public TaxForm loadInputStream(ReportForm reportForm, InputStream inputStream) throws XmlException {
        try {
            Document doc = documentBuilder.parse(inputStream);
            return load(reportForm, doc);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            LOG.error(e.getMessage());
            throw new XmlException("Can't load this input stream", e);
        }
    }

    private static TaxForm load(ReportForm reportForm, Document doc) throws ParserConfigurationException, IOException, SAXException {
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("DECLAR");
        return getTaxDeclFOP(reportForm, nodeList.item(0));
    }

    private static TaxForm getTaxDeclFOP(ReportForm reportForm, Node node) {
        TaxForm taxForm = new TaxForm();
        Element element = (Element) node;

        taxForm.setDeclarHead(getDeclarHead(reportForm.getHead(), element));
        taxForm.setDeclarBody(getDeclarBody(reportForm.getBody(), element));

        return taxForm;
    }

    private static SubElement getDeclarHead(List<ReportValue> headMap, Element element) {
        NodeList nodeList = element.getElementsByTagName("DECLARHEAD");
        Element declarHeadElement = (Element) nodeList.item(0);
        return setDeclarElement(headMap, declarHeadElement);
    }

    private static SubElement getDeclarBody(List<ReportValue> bodyMap, Element element) {
        NodeList visParamsList = element.getElementsByTagName("DECLARBODY");
        Element declarbodyParameters = (Element) visParamsList.item(0);
        return setDeclarElement(bodyMap, declarbodyParameters);
    }

    private static SubElement setDeclarElement(List<ReportValue> map, Element parameters) {
        SubElement subElement = new SubElement();
        for (ReportValue entry : map) {
            switch (entry.type) {
                case "String":
                    addStringParameter(subElement, parameters, entry.name);
                    break;
                case "Long":
                    addLongParameter(subElement, parameters, entry.name);
                    break;
                case "Int":
                    addIntParameter(subElement, parameters, entry.name);
                    break;
                case "Double":
                    addDoubleParameter(subElement, parameters, entry.name);
                    break;
                case "LinkedDoc":
                    setLinkedDocs(subElement, parameters);
                    break;
            }
        }
        return subElement;
    }

    private static void setLinkedDocs(SubElement subElementClass, Element element) {
        Element subElement = (Element) element.getElementsByTagName("LINKED_DOCS").item(0);
        if(subElement!=null) {
            NodeList nodeList = subElement.getElementsByTagName("DOC");
            List<LinkedDoc> linkedDocs = new ArrayList<>();
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                    linkedDocs.add(getLinkedDoc(node));
            }
            subElementClass.addParameter("LINKED_DOCS", linkedDocs);
        }
    }

    private static LinkedDoc getLinkedDoc(Node node) {
        LinkedDoc linkedDoc = new LinkedDoc();
        Element element = (Element) node;
        linkedDoc.setNum(element.getAttributes().getNamedItem("NUM").getNodeValue());
        linkedDoc.setTypeLinkDOC(element.getAttributes().getNamedItem("TYPE").getNodeValue());
        linkedDoc.setDoc(getStringElement(element, "C_DOC"));
        linkedDoc.setDocSub(getStringElement(element, "C_DOC_SUB"));
        linkedDoc.setDocVer(getStringElement(element, "C_DOC_VER"));
        linkedDoc.setDocType(getStringElement(element, "C_DOC_TYPE"));
        linkedDoc.setDocCnt(getStringElement(element, "C_DOC_CNT"));
        linkedDoc.setDocStan(getStringElement(element, "C_DOC_STAN"));
        linkedDoc.setFilename(getStringElement(element, "FILENAME"));
        return linkedDoc;
    }

    private static void addIntParameter(SubElement subElementClass, Element element, String elementName) {
        String strElement = getStringElement(element, elementName);
        if (strElement != null && strElement.length() > 0) {
            subElementClass.addParameter(
                    elementName, List.of(Integer.valueOf(strElement))
            );
        }
    }

    private static void addLongParameter(SubElement subElementClass, Element element, String elementName) {
        String strElement = getStringElement(element, elementName);
        if (strElement != null && strElement.length() > 0) {
            subElementClass.addParameter(
                    elementName, List.of(Long.valueOf(strElement))
            );
        }
    }

    private static void addDoubleParameter(SubElement subElementClass, Element element, String elementName) {
        String strElement = getStringElement(element, elementName);
        if (strElement != null && strElement.length() > 0) {
            subElementClass.addParameter(
                    elementName, List.of(Double.parseDouble(strElement))
            );
        }
    }

    private static void addStringParameter(SubElement subElementClass, Element element, String elementName) {
        if (elementName != null && elementName.length() > 0) {
            String data = getStringElement(
                    element, elementName
            );
            if (data != null)
                subElementClass.addParameter(
                        elementName, List.of(
                                data
                        )
                );
        }
    }

    private static int getIntElement(Element element, String elementName) {
        String strElement = getStringElement(element, elementName);
        if (strElement != null && strElement.length() > 0) {
            return Integer.parseInt(strElement);
        } else return 0;
    }

    private static String getStringElement(Element element, String elementName) {
        Node node = element
                .getElementsByTagName(elementName)
                .item(0);
        if (node != null) return node.getTextContent();
        else return null;
    }

    public static void main(String[] args) throws XmlException {
        String xmlFileName = "zvit_main.XML";
        ReadXmlDOMController domController = new ReadXmlDOMController();
        TaxForm taxForm;
        ReportForm reportForm = new F0103405();
        try {
            taxForm = domController.loadFile(reportForm, xmlFileName);
            System.out.println(taxForm);
            WriteXmlStAXController writeXmlStAXController = new WriteXmlStAXController();
            writeXmlStAXController.writeToXML(taxForm, reportForm, "out.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
