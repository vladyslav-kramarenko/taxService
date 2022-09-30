package com.my.kramarenko.taxService.xml.taxDeclFop;

public class TaxDeclFOP {
    private SubElement declarHead;
    private SubElement declarBody;

    public void setDeclarBody(SubElement declarBody) {
        this.declarBody = declarBody;
    }

    public SubElement getDeclarHead() {
        return declarHead;
    }



    public void setDeclarHead(SubElement declarHead) {
        this.declarHead = declarHead;
    }

    public SubElement getDeclarBody() {
        return declarBody;
    }

    @Override
    public String toString() {
        return "TaxDeclFOP{" +
                "declarHead " + declarHead +
                "\n, declarBody " + declarBody +
                '}';
    }
}
