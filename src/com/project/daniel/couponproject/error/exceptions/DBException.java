package com.project.daniel.couponproject.error.exceptions;

public class DBException extends Exception{
    public DBException(final String msg) {
        super("Failed to perform DB operation");
    }
}
