package com.example.transactionbank.repository;

import com.example.transactionbank.domain.User;
import com.example.transactionbank.util.CreateUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Return user saved when successful")
    void saveUser_ReturnUserSaved_WhenSuccessful(){

        User user = CreateUser.saved();
        User usersaved = this.userRepository.save(user);

        Assertions.assertThat(usersaved)
                .isNotNull()
                .isEqualTo(user);

        Assertions.assertThat(usersaved.getName()).isEqualTo(user.getName());
        Assertions.assertThat(usersaved.getUserType()).isEqualTo(user.getUserType());
        Assertions.assertThat(usersaved.getCpf()).isEqualTo(user.getCpf());
        Assertions.assertThat(usersaved.getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(usersaved.getBalance()).isEqualTo(user.getBalance());
        Assertions.assertThat(usersaved.getPasswod()).isEqualTo(user.getPasswod());
    }

    @Test
    @DisplayName("Return user find by id when successful")
    void findById_ReturnUser_whenSuccessful(){

        User user = CreateUser.saved();
        User usersaved = this.userRepository.save(user);

        Optional<User> userByid = this.userRepository.findById(usersaved.getId());

        Assertions.assertThat(userByid)
                .isNotEmpty()
                .contains(usersaved);
    }

    @Test
    @DisplayName("Return optional.empty when user not found")
    void findById_ReturnOptionalEmpty_WhenNotSuccessfull(){

        User usersaved = CreateUser.saved();

        Long idseacher = CreateUser.savedSecondValidUser().getId();

        userRepository.save(usersaved);

        Optional<User> optionalempty = this.userRepository.findById(idseacher);
        Assertions.assertThat(optionalempty)
                .isEmpty();
    }

    @Test
    @DisplayName("Return user by doucument when successful")
    void searchByDoucument_ReturnUser_WhenSuccessful(){

        User usersaved = userRepository.save(CreateUser.saved());

        Optional<User> userexpected = userRepository.searchByDoucument(usersaved.getCpf());

        Assertions.assertThat(userexpected)
                .isNotEmpty()
                .contains(usersaved);
    }

    @Test
    @DisplayName("Return oprional empty when not successful")
    void searchByDoucument_ReturnoptionalEmpty_WhenNotSuccessful(){

        User usersaved = CreateUser.saved();

        String cpfseacher = CreateUser.savedSecondValidUser().getCpf();

        userRepository.save(usersaved);

        Optional<User> optionalempty = this.userRepository.searchByDoucument(cpfseacher);
        Assertions.assertThat(optionalempty)
                .isEmpty();
    }
}