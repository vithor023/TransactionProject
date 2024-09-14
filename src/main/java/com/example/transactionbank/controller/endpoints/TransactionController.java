package com.example.transactionbank.controller.endpoints;

import com.example.transactionbank.domain.Transaction;
import com.example.transactionbank.domain.dto.TransactionDTO;
import com.example.transactionbank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO){

        Transaction transactionsaved = transactionService.saveTransaction(transactionDTO);
        return new ResponseEntity<>(transactionsaved, HttpStatus.CREATED);
    }
}
