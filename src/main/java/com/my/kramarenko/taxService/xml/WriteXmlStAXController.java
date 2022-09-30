package com.my.kramarenko.taxService.xml.taxDeclFop;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WriteXmlStAXController {

    public void writeToXML(TaxDeclFOP taxDeclFOP, String fileName) throws XMLStreamException, IOException {
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        FileOutputStream out=null;
        XMLStreamWriter writer = null;
        try  {
            out = new FileOutputStream(fileName);
            writer = output.createXMLStreamWriter(out);

            writer.writeStartDocument("windows-1251", "1.0");
            writer.writeStartElement("DECLAR");
//            writer.writeAttribute("xmlns", "http://www.nure.ua");
            writer.writeAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            writer.writeAttribute(" xsi:noNamespaceSchemaLocation", "F0103405.xsd");

            writer.writeStartElement("DECLARHEAD");

            for (Map.Entry<String, List> entry : taxDeclFOP.getDeclarHead().getEntrySet()) {
                if (entry.getKey().equals("LINKED_DOCS")) {
                    writer.writeStartElement("LINKED_DOCS");
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        writer.writeStartElement("DOC");

                        LinkedDoc doc = (LinkedDoc) entry.getValue().get(i);
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
                } else {
                    writer.writeStartElement(entry.getKey());
                    writer.writeCharacters(entry.getValue().get(0).toString());
                    System.out.println("write element ["+entry.getKey()+"]");
                    writer.writeEndElement();
                }
            }

            writer.writeEndElement();//DECLARHEAD

            writer.writeStartElement("DECLARBODY");

            for (Map.Entry<String, List> entry : taxDeclFOP.getDeclarBody().getEntrySet()) {
                writer.writeStartElement(entry.getKey());
                writer.writeCharacters(entry.getValue().get(0).toString());
                writer.writeEndElement();
            }

            writer.writeEndElement();//DECLARBODY


            writer.writeEndElement();//DECLAR

//        if (flower.getVisualParameters().getAveLenFlower() != 0) {
//            writer.writeStartElement("aveLenFlower");
//            writer.writeAttribute("measure", VisualParameters.AVE_LEN_FLOWER_MEASURE);
//            writer.writeCharacters(String.valueOf(flower.getVisualParameters().getAveLenFlower()));
//            writer.writeEndElement();
//        }

//        writer.writeEndElement();

//        writer.writeStartElement("growingTips");
//
//        writer.writeStartElement("tempreture");
//        writer.writeAttribute("measure", GrowingTips.TEMPRETURE_MEASURE);
//        writer.writeCharacters(String.valueOf(flower.getGrowingTips().getTempreture()));
//        writer.writeEndElement();
//
//        writer.writeEmptyElement("lighting");
//        writer.writeAttribute("lightRequiring", flower.getGrowingTips().getLightRequiring().getValue());
//
//        if (flower.getGrowingTips().getWatering() != 0) {
//            writer.writeStartElement("watering");
//            writer.writeAttribute("measure", GrowingTips.WATERING_MEASURE);
//            writer.writeCharacters(String.valueOf(flower.getGrowingTips().getWatering()));
//            writer.writeEndElement();
//        }
//        writer.writeEndElement();
//
//        writer.writeStartElement("multiplying");
//        writer.writeCharacters(flower.getMultiplying().getName());
//        writer.writeEndElement();

//            writer.writeEndElement();
//        }


        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }finally {
            writer.flush();
            writer.close();
            out.close();
        }
    }
}
