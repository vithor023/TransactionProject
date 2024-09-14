package com.example.transactionbank.util;

import com.example.transactionbank.domain.User;
import com.example.transactionbank.domain.enuns.UserType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class CreateUser {

    public static User saved(){

        User user = new User();
        user.setName("Joao");
        user.setBalance(BigDecimal.valueOf(122.87));
        user.setCpf("123.456.432-09");
        user.setEmail("joao@gamil.com");
        user.setUserType(UserType.COMMON);
        user.setPasswod("123432");

        return user;
    }

    public static User notValidUser(){

        User user = new User();
        user.setBalance(BigDecimal.valueOf(122.87));
        user.setCpf("123.456.432-09");
        user.setEmail("joao@gamil.com");
        user.setUserType(UserType.COMMON);
        user.setPasswod("123432");

        return user;
    }

    public static User savedValid(){

        User user = new User();
        user.setId(1L);
        user.setName("Joao");
        user.setBalance(BigDecimal.valueOf(123.12));
        user.setCpf("123.456.432-09");
        user.setEmail("joao@gamil.com");
        user.setUserType(UserType.COMMON);
        user.setPasswod("123432");

        return user;
    }

    public static User savedSecondUser(){

        User user = new User();
        user.setName("guilherme");
        user.setBalance(BigDecimal.valueOf(765.87));
        user.setCpf("121.193.987-05");
        user.setEmail("guilherme@gamil.com");
        user.setUserType(UserType.MERCHANT);
        user.setPasswod("1323113");

        return user;
    }

    public static User savedSecondValidUser() {

        User user = new User();
        user.setId(2L);
        user.setName("guilherme");
        user.setBalance(BigDecimal.valueOf(765.87));
        user.setCpf("121.193.987-05");
        user.setEmail("guilherme@gamil.com");
        user.setUserType(UserType.MERCHANT);
        user.setPasswod("1323113");

        return user;
    }

}
