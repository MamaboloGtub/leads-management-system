package com.mamabologtub.leads_management_system.exceptions;

public class LeadNotFoundException extends BaseException {
    public LeadNotFoundException(Long id) {
        super("Lead with ID " + id + " not found", 404);
    }
}
