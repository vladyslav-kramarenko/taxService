package com.my.kramarenko.taxService.exception;

public class CommandException extends Exception {
    int errorCode;

    public CommandException() { }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable e) {
        super(message, e);
    }
}
