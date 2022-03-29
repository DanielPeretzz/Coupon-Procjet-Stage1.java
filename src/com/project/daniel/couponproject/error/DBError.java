package com.project.daniel.couponproject.error;

public class DBError extends Error {

    public DBError(final DBType dbType){
        super("Failed to perform Data-Base operation: " + "Failed to " +  dbType);
    }
}
