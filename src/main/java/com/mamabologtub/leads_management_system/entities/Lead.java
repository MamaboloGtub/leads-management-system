package com.mamabologtub.leads_management_system.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import lombok.Data;

/**
 * @Author Tshepo M Mahudu on Oct 18, 2025.
 */

@Data
@Entity
public class Lead {

    @Id
    private Long id;

    private String name;

    @Email
    private String email;

    private String leadSource;

    private String leadStatus;

    private LocalDateTime createAt;
    private LocalDateTime updatedAt;


}
