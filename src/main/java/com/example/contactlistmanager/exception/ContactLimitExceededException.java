package com.example.contactlistmanager.exception;

public class ContactLimitExceededException extends RuntimeException{
    public ContactLimitExceededException(String message) {
        super(message);
    }
}
