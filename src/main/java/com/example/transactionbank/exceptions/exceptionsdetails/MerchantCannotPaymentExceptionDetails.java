package com.example.transactionbank.exceptions.exceptionsdetails;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Builder
public class MerchantCannotPaymentExceptionDetails{
    private String title;
    private int status;
    private String details;
    private String developerMessage;
    private LocalDateTime localDateTime;
}
