package com.mamabologtub.leads_management_system.exceptions;

public class DuplicateEmailException extends BaseException {
    public DuplicateEmailException(String email) {
        super("Lead with email '" + email + "' already exists", 409);
    }
}
