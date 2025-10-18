package com.mamabologtub.leads_management_system.services.impl;

import org.springframework.stereotype.Service;

import com.mamabologtub.leads_management_system.dtos.LeadsDto;
import com.mamabologtub.leads_management_system.entities.Lead;
import com.mamabologtub.leads_management_system.repositories.LeadsRepository;
import com.mamabologtub.leads_management_system.services.LeadsService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Author Tshepo M Mahudu on Oct 18, 2025.
 */
@Service
public class LeadsServiceImpl implements LeadsService {

    private final LeadsRepository leadsRepository;

    public LeadsServiceImpl(LeadsRepository repository) {
        this.leadsRepository = repository;
    }


    @Override
    public Flux<Lead> getLeads() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<Lead> createLeads(LeadsDto leadsDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<LeadsDto> findLeadById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<Lead> updateLead(Long id, LeadsDto leadsDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<Void> deleteLead(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

}
