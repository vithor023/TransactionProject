package com.example.transactionbank.repository;

import com.example.transactionbank.domain.Transaction;
import com.example.transactionbank.util.CreateTransaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    @DisplayName("Return saved transaction when successful ")
    void saveTransaction_ReturnSavedTransaction_WhenSuccessful(){

        Transaction transactionsaved = transactionRepository.save(CreateTransaction.saved());

        Transaction transactionexpected = CreateTransaction.savedValid();

        Assertions.assertThat(transactionsaved).isNotNull()
                .isEqualTo(transactionexpected);

        Assertions.assertThat(transactionsaved.getBalance()).isEqualTo(transactionexpected.getBalance());
        Assertions.assertThat(transactionsaved.getId()).isEqualTo(transactionexpected.getId());
        Assertions.assertThat(transactionsaved.getPayee()).isEqualTo(transactionexpected.getPayee());
        Assertions.assertThat(transactionsaved.getPayer()).isEqualTo(transactionexpected.getPayer());
    }
}