package com.my.kramarenko.taxService.exception;

public class DBException extends Exception {
    public DBException(String message, Exception e) {
        super(message, e);
    }

    public DBException(String message) {
        super(message);
    }
}
