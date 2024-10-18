package com.example.contactlistmanager.exception;

public class InvalidPhoneNumberLengthException extends RuntimeException{
    public InvalidPhoneNumberLengthException(String message) {
        super(message);
    }
}
