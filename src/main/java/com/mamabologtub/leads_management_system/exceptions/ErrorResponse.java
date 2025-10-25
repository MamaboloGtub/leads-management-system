package com.mamabologtub.leads_management_system.exceptions;

import lombok.Builder;
import lombok.Data;

/**
 * @Author Tshepo M Mahudu on Oct 20, 2025.
 */
@Data
@Builder
public class ErrorResponse {
    private String message;
    private Integer code;
    private String source;
}
