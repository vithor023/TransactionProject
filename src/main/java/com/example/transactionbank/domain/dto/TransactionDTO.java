package com.example.transactionbank.domain.dto;

import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public record TransactionDTO(

        @NotEmpty(message = "this field cannot be empty")
        BigDecimal balance,

        @NotEmpty(message = "this field cannot be empty")
        Long payer_id,

        @NotEmpty(message = "this field cannot be empty")
        Long payee_id
) {
}
