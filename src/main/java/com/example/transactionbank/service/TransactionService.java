package com.example.transactionbank.service;

import com.example.transactionbank.domain.Transaction;
import com.example.transactionbank.domain.User;
import com.example.transactionbank.domain.dto.TransactionDTO;
import com.example.transactionbank.exceptions.PayerInvalidException;
import com.example.transactionbank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final UserService userService;

    public Transaction saveTransaction(TransactionDTO transactionDTO){

        User payer = userService.findById(transactionDTO.payer_id());
        User payee = userService.findById(transactionDTO.payee_id());

        if(!userService.userValidate(payer,transactionDTO.balance())){
            throw new PayerInvalidException();
        }

        Transaction transaction = new Transaction();

        transaction.setBalance(transactionDTO.balance());
        transaction.setPayee(payee);
        transaction.setPayer(payer);

        payer.setBalance(payer.getBalance().subtract(transaction.getBalance()));
        payee.setBalance(payee.getBalance().add(transaction.getBalance()));

        return transactionRepository.save(transaction);
    }
}
