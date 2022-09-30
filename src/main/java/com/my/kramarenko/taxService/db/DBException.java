package com.my.kramarenko.taxService.db;

import org.apache.log4j.Logger;

public class DBException extends Exception {
    public DBException(String message, Exception e) {
        super(message, e);
    }
}
