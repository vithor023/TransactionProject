package com.example.transactionbank.domain;

import com.example.transactionbank.domain.enuns.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@Setter
@Getter
@Table(name = "Users")
@Entity(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;

    private String passwod;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private UserType userType;
}
