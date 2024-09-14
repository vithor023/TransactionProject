package com.example.transactionbank.util;

import com.example.transactionbank.domain.Transaction;
import com.example.transactionbank.domain.dto.TransactionDTO;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class CreateTransaction {

    public static Transaction saved(){

        Transaction transaction = new Transaction();
        transaction.setBalance(BigDecimal.valueOf(122.87));
        transaction.setPayer(CreateUser.savedValid());
        transaction.setPayee(CreateUser.savedSecondValidUser());

        return transaction;
    }

    public static Transaction savedValid(){

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setBalance(BigDecimal.valueOf(122.87));
        transaction.setPayer(CreateUser.savedValid());
        transaction.setPayee(CreateUser.savedSecondValidUser());

        return transaction;
    }

    public static TransactionDTO saveDTO(){

        return new TransactionDTO(BigDecimal.valueOf(123.12),
                CreateUser.savedValid().getId(),
                CreateUser.savedSecondValidUser().getId());
    }
}
