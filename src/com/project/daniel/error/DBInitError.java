package com.project.daniel.error;

public class DBInitError extends Error{
    public DBInitError() {
        super("Failed to initialize database");
    }
}
