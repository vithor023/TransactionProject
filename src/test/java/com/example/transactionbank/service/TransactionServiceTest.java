package com.example.transactionbank.service;

import com.example.transactionbank.domain.Transaction;
import com.example.transactionbank.domain.User;
import com.example.transactionbank.domain.dto.TransactionDTO;
import com.example.transactionbank.exceptions.PayerInvalidException;
import com.example.transactionbank.repository.TransactionRepository;
import com.example.transactionbank.util.CreateTransaction;
import com.example.transactionbank.util.CreateUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp(){

        BDDMockito.when(userService.findById(ArgumentMatchers.anyLong()))
                        .thenReturn(CreateUser.savedSecondValidUser());

        BDDMockito.when(userService.findById(ArgumentMatchers.anyLong()))
                        .thenReturn(CreateUser.savedValid());

        BDDMockito.when(userService.userValidate(ArgumentMatchers.any(),ArgumentMatchers.any()))
                        .thenReturn(true);

        BDDMockito.when(transactionRepository.save(ArgumentMatchers.any(Transaction.class)))
                .thenReturn(CreateTransaction.savedValid());
    }

    @Test
    @DisplayName("Transaction create when successful")
    void saveTransaction_SaveTransaction_WhenSuccessful(){

        User payer= CreateUser.savedValid();
        User payee = CreateUser.savedSecondValidUser();

        Transaction transactionexpected = CreateTransaction.savedValid();

        Transaction transactionsaved = transactionService.saveTransaction(CreateTransaction.saveDTO());

        payer.setBalance(BigDecimal.valueOf(0));
        payee.setBalance(BigDecimal.valueOf(888.74));

        BDDMockito.verify(transactionRepository,Mockito.times(1))
                        .save(ArgumentMatchers.any(Transaction.class));

        Assertions.assertThat(transactionsaved).isNotNull()
                .isEqualTo(transactionexpected);

        Assertions.assertThat(transactionsaved.getPayer()).isEqualTo(transactionexpected.getPayer());
        Assertions.assertThat(transactionsaved.getPayee()).isEqualTo(transactionexpected.getPayee());
        Assertions.assertThat(transactionsaved.getBalance()).isEqualTo(transactionexpected.getBalance());
        Assertions.assertThat(transactionsaved.getId()).isEqualTo(transactionexpected.getId());
    }

    @Test
    @DisplayName("Throw PayerInvalidException when not successful")
    void saveTransaction_ThrowPayerInvalidException_whenNotSuccessful(){

        BDDMockito.when(userService.userValidate(ArgumentMatchers.any(),ArgumentMatchers.any()))
                .thenReturn(false);

        TransactionDTO transactionsaved = CreateTransaction.saveDTO();

        Assertions.assertThatExceptionOfType(PayerInvalidException.class)
                .isThrownBy(() -> transactionService.saveTransaction(transactionsaved));
    }
}