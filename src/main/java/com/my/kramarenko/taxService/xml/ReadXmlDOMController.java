package com.my.kramarenko.taxService.xml.taxDeclFop;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller for DOM parser.
 */
public class ReadXmlDOMController {

    public TaxDeclFOP loadFile(ReportForm reportForm, String xmlFileName) throws ParserConfigurationException, IOException, SAXException {
        File inputFile = new File(xmlFileName);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(inputFile);
        return load(reportForm, doc);
    }

//    public TaxDeclFOP loadInputStream(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
//        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//        Document doc = documentBuilder.parse(inputStream);
//        return load(doc);
//    }

    public TaxDeclFOP loadInputStream(ReportForm reportForm, InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(inputStream);
        return load(reportForm, doc);
    }

    public TaxDeclFOP load(ReportForm reportForm, Document doc) throws ParserConfigurationException, IOException, SAXException {
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("DECLAR");
        return getTaxDeclFOP(reportForm, nodeList.item(0));
    }

//    public TaxDeclFOP load(Document doc) throws ParserConfigurationException, IOException, SAXException {
//        doc.getDocumentElement().normalize();
//        NodeList nodeList = doc.getElementsByTagName("DECLAR");
//        return getTaxDeclFOP(nodeList.item(0));
//    }

    private static void setDeclarElement(Map<String, String> map, SubElement subElement, Element parameters) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            switch (entry.getValue()) {
                case "String":
                    addStringParameter(subElement, parameters, entry.getKey());
                    break;
                case "Long":
                    addLongParameter(subElement, parameters, entry.getKey());
                    break;
                case "Int":
                    addIntParameter(subElement, parameters, entry.getKey());
                    break;
                case "Double":
                    addDoubleParameter(subElement, parameters, entry.getKey());
                    break;
                case "LinkedDock":
                    setLinkedDocs(subElement, parameters);
                    break;
            }
        }
    }

    private static SubElement getDeclarBody(Map<String, String> bodyMap, Element element) {
        NodeList visParamsList = element.getElementsByTagName("DECLARBODY");
        Element declarbodyParameters = (Element) visParamsList.item(0);
        SubElement declarbody = new SubElement();
        setDeclarElement(bodyMap, declarbody, declarbodyParameters);
        return declarbody;
    }

//    private static SubElement getDeclarBody(Element element) {
//        NodeList visParamsList = element.getElementsByTagName("DECLARBODY");
//
//        Element declarbodyParameters = (Element) visParamsList.item(0);
//
//        SubElement declarbody = new SubElement();
//
//        addLongParameter(declarbody, declarbodyParameters, "HTIN");
//        addStringParameter(declarbody, declarbodyParameters, "HNAME");
//        addStringParameter(declarbody, declarbodyParameters, "HF");
//        addStringParameter(declarbody, declarbodyParameters, "HLOC");
//        addStringParameter(declarbody, declarbodyParameters, "HTEL");
//        addIntParameter(declarbody, declarbodyParameters, "HKSTI");
//        addIntParameter(declarbody, declarbodyParameters, "HKBOS");
//        addStringParameter(declarbody, declarbodyParameters, "HBOS");
//        addStringParameter(declarbody, declarbodyParameters, "HKVED");
//        addStringParameter(declarbody, declarbodyParameters, "HKEXECUTOR");
//        addIntParameter(declarbody, declarbodyParameters, "HZIP");
//        addStringParameter(declarbody, declarbodyParameters, "HREG");
//        addStringParameter(declarbody, declarbodyParameters, "HRAJ");
//        addStringParameter(declarbody, declarbodyParameters, "HCITY");
//        addStringParameter(declarbody, declarbodyParameters, "HSTREET");
//        addStringParameter(declarbody, declarbodyParameters, "HBUILD");
//        addStringParameter(declarbody, declarbodyParameters, "HCORP");
//        addStringParameter(declarbody, declarbodyParameters, "HAPT");
//        addStringParameter(declarbody, declarbodyParameters, "HLNAME");
//        addStringParameter(declarbody, declarbodyParameters, "HPNAME");
//        addStringParameter(declarbody, declarbodyParameters, "HFNAME");
//        addStringParameter(declarbody, declarbodyParameters, "HCOUNTRY");
//        addStringParameter(declarbody, declarbodyParameters, "HEMAIL");
//        addStringParameter(declarbody, declarbodyParameters, "HFILL");
//        addStringParameter(declarbody, declarbodyParameters, "HZ");
//        addStringParameter(declarbody, declarbodyParameters, "HY");
//        addIntParameter(declarbody, declarbodyParameters, "HZY");
//        addStringParameter(declarbody, declarbodyParameters, "HSTI");
//
////        I. ЗАГАЛЬНІ ПОКАЗНИКИ ПІДПРИЄМНИЦЬКОЇ ДІЯЛЬНОСТІ
//        addIntParameter(declarbody, declarbodyParameters, "HNACTL");
//
////        ІІ. ПОКАЗНИКИ ГОСПОДАРСЬКОЇ ДІЯЛЬНОСТІ ДЛЯ ПЛАТНИКІВ ЄДИНОГО ПОДАТКУ ПЕРШОЇ ГРУПИ
//        addDoubleParameter(declarbody, declarbodyParameters, "R02G1");
//        addDoubleParameter(declarbody, declarbodyParameters, "R02G2");
//        addDoubleParameter(declarbody, declarbodyParameters, "R02G3");
//        addDoubleParameter(declarbody, declarbodyParameters, "R02G4");
//        addDoubleParameter(declarbody, declarbodyParameters, "R001G3");
//        addDoubleParameter(declarbody, declarbodyParameters, "R002G3");
//
////ІІІ. ПОКАЗНИКИ ГОСПОДАРСЬКОЇ ДІЯЛЬНОСТІ ДЛЯ ПЛАТНИКІВ ЄДИНОГО ПОДАТКУ ДРУГОЇ ГРУПИ
//        addDoubleParameter(declarbody, declarbodyParameters, "R03G1");
//        addDoubleParameter(declarbody, declarbodyParameters, "R03G2");
//        addDoubleParameter(declarbody, declarbodyParameters, "R03G3");
//        addDoubleParameter(declarbody, declarbodyParameters, "R03G4");
//        addDoubleParameter(declarbody, declarbodyParameters, "R003G3");
//        addDoubleParameter(declarbody, declarbodyParameters, "R004G3");
//
////        ІV. ПОКАЗНИКИ ГОСПОДАРСЬКОЇ ДІЯЛЬНОСТІ ДЛЯ ПЛАТНИКІВ ЄДИНОГО ПОДАТКУ ТРЕТЬОЇ ГРУПИ
//        addDoubleParameter(declarbody, declarbodyParameters, "R005G3");
//        addDoubleParameter(declarbody, declarbodyParameters, "R006G3");
//        addDoubleParameter(declarbody, declarbodyParameters, "R007G3");
//
////        V. ВИЗНАЧЕННЯ ПОДАТКОВИХ ЗОБОВ'ЯЗАНЬ ПО ЄДИНОМУ ПОДАТКУ
//        addDoubleParameter(declarbody, declarbodyParameters, "R008G3");
//        addDoubleParameter(declarbody, declarbodyParameters, "R009G3");
//        addDoubleParameter(declarbody, declarbodyParameters, "R010G3");
//        addDoubleParameter(declarbody, declarbodyParameters, "R011G3");
//        addDoubleParameter(declarbody, declarbodyParameters, "R012G3");
//        addDoubleParameter(declarbody, declarbodyParameters, "R013G3");
//        addDoubleParameter(declarbody, declarbodyParameters, "R014G3");
//
//        addDoubleParameter(declarbody, declarbodyParameters, "HZY");
//        addDoubleParameter(declarbody, declarbodyParameters, "HZY");
//
//        addDoubleParameter(declarbody, declarbodyParameters, "HZY");
//
//
//        return declarbody;
//    }

    private static TaxDeclFOP getTaxDeclFOP(ReportForm reportForm, Node node) {
        TaxDeclFOP taxDeclFOP = new TaxDeclFOP();
        Element element = (Element) node;

        taxDeclFOP.setDeclarHead(getDeclarHead(reportForm.head, element));
        taxDeclFOP.setDeclarBody(getDeclarBody(reportForm.body, element));

        return taxDeclFOP;
    }

//    private static TaxDeclFOP getTaxDeclFOP(Node node) {
//        TaxDeclFOP taxDeclFOP = new TaxDeclFOP();
//        Element element = (Element) node;
//
//        taxDeclFOP.setDeclarHead(getDeclarHead(element));
//        taxDeclFOP.setDeclarBody(getDeclarBody(element));
//
//        return taxDeclFOP;
//    }

    private static SubElement getDeclarHead(Map<String, String> headMap, Element element) {
        NodeList nodeList = element.getElementsByTagName("DECLARHEAD");

        Element declarHeadElement = (Element) nodeList.item(0);

        SubElement declarhead = new SubElement();

        setDeclarElement(headMap, declarhead, declarHeadElement);
        return declarhead;
    }

//    private static SubElement getDeclarHead(Element element) {
//        NodeList nodeList = element.getElementsByTagName("DECLARHEAD");
//
//        Element declarHeadElement = (Element) nodeList.item(0);
//
//        SubElement declarhead = new SubElement();
//
//        addLongParameter(declarhead, declarHeadElement, "TIN");
//        addStringParameter(declarhead, declarHeadElement, "C_DOC");
//        addStringParameter(declarhead, declarHeadElement, "C_DOC_SUB");
//        addStringParameter(declarhead, declarHeadElement, "C_DOC_VER");
//        addIntParameter(declarhead, declarHeadElement, "C_DOC_TYPE");
//        addIntParameter(declarhead, declarHeadElement, "C_DOC_CNT");
//        addIntParameter(declarhead, declarHeadElement, "C_REG");
//        addIntParameter(declarhead, declarHeadElement, "C_RAJ");
//        addIntParameter(declarhead, declarHeadElement, "PERIOD_MONTH");
//        addIntParameter(declarhead, declarHeadElement, "PERIOD_TYPE");
//        addIntParameter(declarhead, declarHeadElement, "PERIOD_YEAR");
//        addIntParameter(declarhead, declarHeadElement, "C_DOC_STAN");
//        setLinkedDocs(declarhead, declarHeadElement);
//        addIntParameter(declarhead, declarHeadElement, "D_FILL");
//        addStringParameter(declarhead, declarHeadElement, "SOFTWARE");
//
//        return declarhead;
//    }

    private static void setLinkedDocs(SubElement subElementClass, Element element) {
        Element subElement = (Element) element.getElementsByTagName("LINKED_DOCS").item(0);
        NodeList nodeList = subElement.getElementsByTagName("DOC");
        List<LinkedDoc> linkedDocs = new ArrayList<>();
        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node node = nodeList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE)
                linkedDocs.add(getLinkedDoc(node));
        }
        subElementClass.addParameter("LINKED_DOCS", linkedDocs);
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


//    private static String getLighingParameter(Element element) {
//        return element
//                .getElementsByTagName("lighting")
//                .item(0).getAttributes().item(0)
//                .getTextContent();
//    }

    private static String getStringElement(Element element, String elementName) {
        Node node = element
                .getElementsByTagName(elementName)
                .item(0);
        if (node != null) return node.getTextContent();
        else return null;
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XMLStreamException {
        String xmlFileName = "zvit_main.XML";
        ReadXmlDOMController domController = new ReadXmlDOMController();
        TaxDeclFOP taxDeclFOP;
        ReportForm reportForm = new F0103405();
//        System.out.println(reportForm.body);
        try {
            taxDeclFOP = domController.loadFile(reportForm, xmlFileName);
//            System.out.println(taxDeclFOP);

            WriteXmlStAXController writeXmlStAXController = new WriteXmlStAXController();
            writeXmlStAXController.writeToXML(taxDeclFOP, "out.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
