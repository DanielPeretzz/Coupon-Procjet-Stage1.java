package com.project.daniel.error.exceptions;

public class UserValidationException extends Exception {

    public UserValidationException() {
        super("Invalid input - Please enter according to the correct format");
    }
}
