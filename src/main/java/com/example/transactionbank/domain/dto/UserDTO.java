package com.example.transactionbank.domain.dto;

import com.example.transactionbank.domain.enuns.UserType;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public record UserDTO(

        @NotEmpty(message = "this field cannot be empty")
        String name,

        @NotEmpty(message = "this field cannot be empty")
        String email,

        @NotEmpty(message = "this field cannot be empty")
        String cpf,

        @NotEmpty(message = "this field cannot be empty")
        String password,

        UserType userType,

        BigDecimal balance
) {
}
