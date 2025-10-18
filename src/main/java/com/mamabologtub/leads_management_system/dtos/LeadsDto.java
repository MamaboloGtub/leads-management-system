package com.mamabologtub.leads_management_system.dtos;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * @Author Tshepo M Mahudu on Oct 18, 2025.
 */

@Data
public class LeadsDto {

    private Long id;

    private String name;

    private String email;

    private String leadSource;

    private String leadStatus;

    private LocalDateTime createAt;

}
