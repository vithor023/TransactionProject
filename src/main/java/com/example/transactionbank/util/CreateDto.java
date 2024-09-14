package com.example.transactionbank.util;

import com.example.transactionbank.domain.User;
import com.example.transactionbank.domain.dto.UserDTO;

public class CreateDto {

    private CreateDto(){

    }
    public static UserDTO userDTO(User user){

        return new UserDTO(user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getPasswod(),
                user.getUserType(),
                user.getBalance());
    }
}
