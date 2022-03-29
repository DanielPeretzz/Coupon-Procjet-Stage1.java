package com.project.daniel.couponproject.error.exceptions;

public class AuthorizationException extends Exception{
    public AuthorizationException(String email) {
        super("this email : " + email + " not have authorize ");
    }
}
