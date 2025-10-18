package com.mamabologtub.leads_management_system.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.mamabologtub.leads_management_system.services.LeadsService;

/**
 * @Author Tshepo M Mahudu on Oct 18, 2025.
 */

@RestController
public class LeadsController {
    private final LeadsService leadsService;

    public LeadsController(LeadsService service) {
        this.leadsService = service;
    }

}
