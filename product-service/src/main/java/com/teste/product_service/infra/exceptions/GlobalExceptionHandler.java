package com.teste.product_service.infra.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(HttpServletRequest request,
                                                                          EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponseDto(
                        LocalDateTime.now(),
                        request.getRequestURI(),
                        request.getMethod(),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        ex.getMessage()
                )
        );
    }

    @ExceptionHandler(ProductIsAlreadyDeactivatedException.class)
    public ResponseEntity<ErrorResponseDto> handleProductIsAlreadyDeactivatedException(HttpServletRequest request,
                                                                                      ProductIsAlreadyDeactivatedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseDto(
                        LocalDateTime.now(),
                        request.getRequestURI(),
                        request.getMethod(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        ex.getMessage()
                )
        );
    }
}
