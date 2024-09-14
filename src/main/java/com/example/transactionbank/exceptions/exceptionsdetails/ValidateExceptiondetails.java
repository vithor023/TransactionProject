package com.example.transactionbank.exceptions.exceptionsdetails;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ValidateExceptiondetails{

    private String title;
    private int status;
    private String details;
    private String developerMessage;
    private LocalDateTime localDateTime;
    private final String fieldError;
    private final String fieldmensage;
}
