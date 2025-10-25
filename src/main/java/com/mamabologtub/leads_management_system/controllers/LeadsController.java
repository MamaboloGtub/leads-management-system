package com.mamabologtub.leads_management_system.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mamabologtub.leads_management_system.dtos.LeadsDto;
import com.mamabologtub.leads_management_system.entities.Lead;
import com.mamabologtub.leads_management_system.services.LeadsService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Author Tshepo M Mahudu on Oct 18, 2025.
 */

@RestController
@RequestMapping("/api/leads")
public class LeadsController {
    private final LeadsService leadsService;

    public LeadsController(LeadsService service) {
        this.leadsService = service;
    }

    @GetMapping
    public ResponseEntity<Flux<LeadsDto>> getAllLeads(LocalDateTime from, LocalDateTime to){
        Flux<LeadsDto> leads = leadsService.getLeads(from, to);
        return ResponseEntity.ok().body(leads);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Lead>> getLeadById(@PathVariable Long id) {
        return leadsService.findLeadById(id)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<ResponseEntity<Lead>> createLead(@RequestBody LeadsDto dto) {
        return leadsService.createLeads(dto).map(l -> ResponseEntity.status(HttpStatus.CREATED).body(l));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Lead>> updateLead(@PathVariable Long id, @RequestBody LeadsDto dto) {
        return leadsService.updateLead(id, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteLeads(@PathVariable Long id) {
        return leadsService.deleteLead(id).then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

}
