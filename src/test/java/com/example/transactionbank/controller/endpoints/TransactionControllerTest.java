package com.example.transactionbank.controller.endpoints;

import com.example.transactionbank.domain.Transaction;
import com.example.transactionbank.domain.dto.TransactionDTO;
import com.example.transactionbank.service.TransactionService;
import com.example.transactionbank.util.CreateTransaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @BeforeEach
    void setUp(){

        BDDMockito.when(transactionService.saveTransaction(ArgumentMatchers.any(TransactionDTO.class)))
                .thenReturn(CreateTransaction.savedValid());
    }

    @Test
    @DisplayName("Return transaction when successful")
    void saveTransaction_ReturnTransaction_WhenSuccessful(){

        Transaction transactionexpected = CreateTransaction.savedValid();

        ResponseEntity<Transaction> transaction = transactionController.createTransaction(CreateTransaction.saveDTO());

        Assertions.assertThat(transaction.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(transaction.getBody()).isEqualTo(transactionexpected);
    }
}