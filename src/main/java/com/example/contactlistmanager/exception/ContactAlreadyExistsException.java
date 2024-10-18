package com.example.contactlistmanager.exception;

public class ContactAlreadyExistsException extends RuntimeException{
    public ContactAlreadyExistsException(String message) {
        super(message);
    }
}
