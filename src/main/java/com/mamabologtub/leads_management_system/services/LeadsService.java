package com.mamabologtub.leads_management_system.services;

import java.time.LocalDateTime;

import com.mamabologtub.leads_management_system.dtos.LeadsDto;
import com.mamabologtub.leads_management_system.entities.Lead;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Author Tshepo M Mahudu on Oct 18, 2025.
 */

public interface LeadsService {

    public Flux<LeadsDto> getLeads(LocalDateTime fromDate, LocalDateTime toDate);
    public Mono<Lead> createLeads(LeadsDto leadsDto);
    public Mono<Lead> findLeadById(Long id);
    public Mono<Lead> updateLead(Long id, LeadsDto leadsDto);
    public Mono<Void> deleteLead(Long id);

}
