package com.example.transactionbank.infra;

import com.example.transactionbank.exceptions.BadRequestException;
import com.example.transactionbank.exceptions.BalanceInsuficientException;
import com.example.transactionbank.exceptions.MerchantCannotPaymentException;
import com.example.transactionbank.exceptions.PayerInvalidException;
import com.example.transactionbank.exceptions.exceptionsdetails.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class HandlerGlobal {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidateExceptiondetails> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException br){
        List<FieldError> fieldErrors = br.getBindingResult().getFieldErrors();

        String errors = fieldErrors.stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));

        String mensagers = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(ValidateExceptiondetails.builder()
                .fieldError(errors)
                .fieldmensage(mensagers)
                .localDateTime(LocalDateTime.now())
                .title("BadRequestException. Check the doucument")
                .details("Check the fields")
                .developerMessage(br.getClass().getName())
                .status(HttpStatus.BAD_REQUEST.value()).build()
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> badRequestExceptionHandler(BadRequestException br){

        return new ResponseEntity<>(BadRequestExceptionDetails.builder()
                .localDateTime(LocalDateTime.now())
                .title("BadRequestException. Check the doucument")
                .details("Check the fields")
                .developerMessage(br.getClass().getName())
                .status(HttpStatus.BAD_REQUEST.value()).build(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BalanceInsuficientException.class)
    public ResponseEntity<BalanceInsuficientExceptionDetails> balanceInsuficientExceptionHandler(BalanceInsuficientException br){

        return new ResponseEntity<>(BalanceInsuficientExceptionDetails.builder()
                .localDateTime(LocalDateTime.now())
                .title("BalanceInsuficientException. Check the doucument")
                .details("Balance insuficeint for transaction")
                .developerMessage(br.getClass().getName())
                .status(HttpStatus.BAD_REQUEST.value()).build(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MerchantCannotPaymentException.class)
    public ResponseEntity<MerchantCannotPaymentExceptionDetails> merchantCannotPaymentException(MerchantCannotPaymentException br){

        return new ResponseEntity<>(MerchantCannotPaymentExceptionDetails.builder()
                .localDateTime(LocalDateTime.now())
                .title("MerchantCannotPaymentException. Check the doucument")
                .details("Merchant cannot make transfers")
                .developerMessage(br.getClass().getName())
                .status(HttpStatus.BAD_REQUEST.value()).build(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PayerInvalidException.class)
    public ResponseEntity<PayerInvalidExceptionDetails> payerInvalidExceptionHandler(PayerInvalidException br){

        return new ResponseEntity<>(PayerInvalidExceptionDetails.builder()
                .localDateTime(LocalDateTime.now())
                .title("PayerInvalidException. Check the doucument")
                .details("Transfer invalid")
                .developerMessage(br.getClass().getName())
                .status(HttpStatus.BAD_REQUEST.value()).build(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DataIntegretViolationExceptionDetails> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException br){

        return new ResponseEntity<>(DataIntegretViolationExceptionDetails.builder()
                .localDateTime(LocalDateTime.now())
                .title("DataIntegrityViolationException. Check the doucument")
                .details("Unique or primary key constraint violation")
                .developerMessage(br.getClass().getName())
                .status(HttpStatus.BAD_REQUEST.value()).build(),HttpStatus.BAD_REQUEST);
    }
}
