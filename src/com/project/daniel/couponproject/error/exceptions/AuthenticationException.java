package com.project.daniel.couponproject.error.exceptions;

import com.project.daniel.couponproject.enums.ClientType;

public class AuthenticationException extends Exception{
    public AuthenticationException(String email, ClientType clientType) {
        super("Failed to Authentication with email : " + email + " of client type : " + clientType);
    }
}
