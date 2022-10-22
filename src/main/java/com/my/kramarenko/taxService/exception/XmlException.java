package com.my.kramarenko.taxService.exception;

public class XmlException extends Exception {
    public XmlException(String message, Exception e) {
        super(message, e);
    }
}
