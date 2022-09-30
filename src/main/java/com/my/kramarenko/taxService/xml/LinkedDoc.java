package com.my.kramarenko.taxService.xml.taxDeclFop;

public class LinkedDoc {

    String doc;
    String docSub;
    String docVer;

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getDocSub() {
        return docSub;
    }

    public void setDocSub(String docSub) {
        this.docSub = docSub;
    }

    public String getDocVer() {
        return docVer;
    }

    public void setDocVer(String docVer) {
        this.docVer = docVer;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocCnt() {
        return docCnt;
    }

    public void setDocCnt(String docCnt) {
        this.docCnt = docCnt;
    }

    public String getDocStan() {
        return docStan;
    }

    public void setDocStan(String docStan) {
        this.docStan = docStan;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTypeLinkDOC() {
        return typeLinkDOC;
    }

    public void setTypeLinkDOC(String typeLinkDOC) {
        this.typeLinkDOC = typeLinkDOC;
    }

    String docType;
    String docCnt;
    String docStan;
    String filename;
    //attributes
    String num;
    String typeLinkDOC;

    @Override
    public String toString() {
        return "\nLinkedDoc{" +
                "\ndoc='" + doc + '\'' +
                ",\n docSub='" + docSub + '\'' +
                ",\n docVer='" + docVer + '\'' +
                ",\n docType='" + docType + '\'' +
                ",\n docCnt='" + docCnt + '\'' +
                ",\n docStan='" + docStan + '\'' +
                ",\n filename='" + filename + '\'' +
                ",\n num='" + num + '\'' +
                ",\n typeLinkDOC='" + typeLinkDOC + '\'' +
                '}';
    }
}
