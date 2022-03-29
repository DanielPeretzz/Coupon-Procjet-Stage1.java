package com.project.daniel.couponproject.error.exceptions;

public class UserValidationException extends ApplicationException {

    public UserValidationException() {
        super("Invalid input - Please enter according to the correct format");
    }
}
