package com.mamabologtub.leads_management_system.services.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mamabologtub.leads_management_system.dtos.LeadsDto;
import com.mamabologtub.leads_management_system.entities.Lead;
import com.mamabologtub.leads_management_system.exceptions.DuplicateEmailException;
import com.mamabologtub.leads_management_system.exceptions.InvalidDataException;
import com.mamabologtub.leads_management_system.exceptions.LeadNotFoundException;
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
                l.getCreatedAt(),
                l.getUpdatedAt()
                ));
    }

    @Override
    public Mono<Lead> createLeads(LeadsDto leadsDto) {
        // Validate input
        if (leadsDto.getName() == null || leadsDto.getName().trim().isEmpty()) {
            return Mono.error(new InvalidDataException("Name is required"));
        }
        if (leadsDto.getEmail() == null || leadsDto.getEmail().trim().isEmpty()) {
            return Mono.error(new InvalidDataException("Email is required"));
        }

        // Check for duplicate email
        return leadsRepository.findByEmail(leadsDto.getEmail())
                .flatMap(existingLead ->
                    Mono.<Lead>error(new DuplicateEmailException(leadsDto.getEmail())))
                .switchIfEmpty(Mono.defer(() -> {
                    Lead lead = mapToDto(leadsDto);
                    return leadsRepository.save(lead);
                }));
    }

    @Override
    public Mono<Lead> findLeadById(Long id) {
        return leadsRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(new LeadNotFoundException(id)));
    }

    @Override
    public Mono<Lead> updateLead(Long id, LeadsDto leadsDto) {
        // Validate input
        if (leadsDto.getName() == null || leadsDto.getName().trim().isEmpty()) {
            return Mono.error(new InvalidDataException("Name is required"));
        }
        if (leadsDto.getEmail() == null || leadsDto.getEmail().trim().isEmpty()) {
            return Mono.error(new InvalidDataException("Email is required"));
        }

        return leadsRepository.findById(id)
                .switchIfEmpty(Mono.error(new LeadNotFoundException(id)))
                .flatMap(existingLead -> {
                    existingLead.setName(leadsDto.getName());
                    existingLead.setEmail(leadsDto.getEmail());
                    existingLead.setLeadSource(leadsDto.getLeadSource());
                    existingLead.setLeadStatus(leadsDto.getLeadStatus());
                    existingLead.setUpdatedAt(LocalDateTime.now());
                    return leadsRepository.save(existingLead);
                });
    }

    @Override
    public Mono<Void> deleteLead(Long id) {
        return leadsRepository.findById(id)
                .switchIfEmpty(Mono.error(new LeadNotFoundException(id)))
                .then(leadsRepository.deleteById(id));
    }

    private Lead mapToDto(LeadsDto dto) {
        Lead entity = new Lead();
        entity.setName(dto.getName());
        entity.setLeadStatus(dto.getLeadStatus());
        entity.setLeadSource(dto.getLeadSource());
        entity.setEmail(dto.getEmail());
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }

}
