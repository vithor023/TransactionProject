package com.example.transactionbank.service;

import com.example.transactionbank.domain.User;
import com.example.transactionbank.domain.dto.UserDTO;
import com.example.transactionbank.domain.enuns.UserType;
import com.example.transactionbank.exceptions.BadRequestException;
import com.example.transactionbank.exceptions.BalanceInsuficientException;
import com.example.transactionbank.exceptions.MerchantCannotPaymentException;
import com.example.transactionbank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(UserDTO userDTO){

        User user = new User();
        user.setName(userDTO.name());
        user.setPasswod(userDTO.password());
        user.setEmail(userDTO.email());
        user.setCpf(userDTO.cpf());
        user.setUserType(userDTO.userType());
        user.setBalance(userDTO.balance());

        return userRepository.save(user);
    }

    public List<User> findAll(){

        return userRepository.findAll();
    }

    public User findById(Long id){

        return userRepository.findById(id).orElseThrow(BadRequestException::new);
    }

    public List<User> findLikeName(String name){

        return userRepository.searchLikeByName(name);
    }

    public User findByDoucument(String cpf){

        return userRepository.searchByDoucument(cpf).orElseThrow(BadRequestException::new);
    }

    public boolean userValidate(User payer, BigDecimal balance){

        if(payer.getUserType() == UserType.MERCHANT){
            throw new MerchantCannotPaymentException();
        }

        if(payer.getBalance().compareTo(balance)<0){
            throw new BalanceInsuficientException();
        }
        return true;
    }
}
