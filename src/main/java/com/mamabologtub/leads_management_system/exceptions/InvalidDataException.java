package com.mamabologtub.leads_management_system.exceptions;

public class InvalidDataException extends BaseException {
    public InvalidDataException(String message) {
        super(message, 400);
    }
}
