package com.mamabologtub.leads_management_system.services.impl;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public Flux<LeadsDto> getLeads(LocalDateTime fromDate, LocalDateTime toDate) {
        Flux<Lead> leads;
        if (fromDate != null && toDate != null) {
            leads = leadsRepository.findByCreatedAtBetween(fromDate, toDate);
        } else {
            leads = leadsRepository.findAll();
        }
        return leads.map(l -> new LeadsDto(
                l.getId(),
                l.getName(),
                l.getEmail(),
                l.getLeadSource(),
                l.getLeadStatus(),
                l.getCreateAt(),
                l.getUpdatedAt()
                ));
    }

    @Override
    public Mono<Lead> createLeads(LeadsDto leadsDto) {
        Lead lead = mapToDto(leadsDto);
        Mono<Lead> savedLead = leadsRepository.save(lead);
        return savedLead;
    }

    @Override
    public Mono<Lead> findLeadById(Long id) {

        return leadsRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Leads with ID " + id + "not found."
                                )));
    }

    @Override
    public Mono<Lead> updateLead(Long id, LeadsDto leadsDto) {
        return leadsRepository.findById(id).flatMap(ex -> {
            ex.setName(leadsDto.getName());
            ex.setEmail(leadsDto.getEmail());
            ex.setLeadSource(leadsDto.getLeadSource());
            ex.setLeadStatus(leadsDto.getLeadStatus());
            ex.setUpdatedAt(leadsDto.getUpdatedAt());
            return leadsRepository.save(ex);
        });
    }

    @Override
    public Mono<Void> deleteLead(Long id) {
        return leadsRepository.deleteById(id);
    }

    private Lead mapToDto(LeadsDto dto) {
        Lead entity = new Lead();
        entity.setName(dto.getName());
        entity.setLeadStatus(dto.getLeadStatus());
        entity.setLeadSource(dto.getLeadSource());
        entity.setEmail(dto.getEmail());
        entity.setCreateAt(dto.getCreateAt());
        return entity;
    }

}
