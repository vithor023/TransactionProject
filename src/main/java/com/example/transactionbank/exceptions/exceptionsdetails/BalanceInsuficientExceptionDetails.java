package com.example.transactionbank.exceptions.exceptionsdetails;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class BalanceInsuficientExceptionDetails{
    private String title;
    private int status;
    private String details;
    private String developerMessage;
    private LocalDateTime localDateTime;
}
