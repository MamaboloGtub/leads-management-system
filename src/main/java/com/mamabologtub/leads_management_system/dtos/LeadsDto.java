package com.mamabologtub.leads_management_system.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Tshepo M Mahudu on Oct 18, 2025.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeadsDto {

    private Long id;

    private String name;

    private String email;

    private String leadSource;

    private String leadStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //        {
    //            "name": "John Doe",
    //            "email": "john@example.com",
    //            "lead_status": "new",
    //            "lead_source": "website"
    //          }
    //    {
    //        "email": "test@example.com",
    //        "password": "password123"
    //      }

}
