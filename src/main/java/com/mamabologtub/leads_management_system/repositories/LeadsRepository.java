package com.mamabologtub.leads_management_system.repositories;

import java.time.LocalDateTime;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.mamabologtub.leads_management_system.entities.Lead;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Author Tshepo M Mahudu on Oct 18, 2025.
 */

public interface LeadsRepository extends ReactiveCrudRepository<Lead, Long> {

    Flux<Lead> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    Mono<Lead> findByEmail(String email);

}
