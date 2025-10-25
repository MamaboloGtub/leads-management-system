package com.mamabologtub.leads_management_system.exceptions;

import javax.management.AttributeNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mamabologtub.leads_management_system.dtos.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @Author Tshepo M Mahudu on Oct 20, 2025.
 */
@Slf4j
@RestControllerAdvice
public class ApiAdvice {

    @Value("${api.source:leads-management-system}")
    private String source;

    @ExceptionHandler(BaseException.class)
    public Mono<ResponseEntity<BaseResponse<ErrorResponse>>> handleCustomException(BaseException exception) {
        HttpStatus status = exception.getCode() == -1
                ? HttpStatus.UNPROCESSABLE_ENTITY
                : HttpStatus.valueOf(exception.getCode());

        return Mono.just(
                ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BaseResponse.<ErrorResponse>builder()
                        .code(status.value())
                        .message(exception.getMessage())
                        .source(source)
                        .build())
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Mono<ResponseEntity<BaseResponse<ErrorResponse>>> jsonParseException(Exception e) {
        log.error("JSON Parse exception: {}", e.getMessage());
        return Mono.just(
                ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.<ErrorResponse>builder()
                        .code(400)
                        .message("Error reading request")
                        .source(source)
                        .build())
                );
    }

    @ExceptionHandler(AttributeNotFoundException.class)
    public Mono<ResponseEntity<BaseResponse<ErrorResponse>>> notFoundException(AttributeNotFoundException e) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseResponse.<ErrorResponse>builder()
                        .code(404)
                        .message("The entity requested does not exist")
                        .source(source)
                        .build())
                );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Mono<ResponseEntity<BaseResponse<ErrorResponse>>> handleDataViolation(DataIntegrityViolationException ex) {
        log.error("Data integrity violation: {}", ex.getMessage());
        return Mono.just(
                ResponseEntity.status(HttpStatus.CONFLICT)
                .body(BaseResponse.<ErrorResponse>builder()
                        .code(409)
                        .message("Data integrity violation - check for duplicate values")
                        .source(source)
                        .build())
                );
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<BaseResponse<ErrorResponse>>> handleGeneral(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return Mono.just(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.<ErrorResponse>builder()
                        .code(500)
                        .message("An unexpected error occurred")
                        .source(source)
                        .build())
                );
    }
}
