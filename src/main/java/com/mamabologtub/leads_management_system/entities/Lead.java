package com.mamabologtub.leads_management_system.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Tshepo M Mahudu on Oct 18, 2025.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("leads")
public class Lead {

    @Id
    private Long id;

    private String name;

    @Email
    private String email;

    @Column("lead_source")
    private String leadSource;

    @Column("lead_status")
    private String leadStatus;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;


}
