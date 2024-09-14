package com.example.transactionbank.integration;

import com.example.transactionbank.domain.User;
import com.example.transactionbank.domain.dto.UserDTO;
import com.example.transactionbank.repository.UserRepository;
import com.example.transactionbank.util.CreateDto;
import com.example.transactionbank.util.CreateUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataJpa
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Return user saved when successful")
    void saveUser_ReturnSavedUser_WhenSuccessful(){

        UserDTO userdtosaved = CreateDto.userDTO(CreateUser.saved());

        ResponseEntity<UserDTO> userResponseEntity = testRestTemplate.exchange("/user/save",HttpMethod.POST, new HttpEntity<>(userdtosaved),UserDTO.class);

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Return list users when successful")
    void findAllusers_ReturnListusers_whenSuccessful(){

        userRepository.save(CreateUser.saved());

        ResponseEntity<List<User>> allUsers = testRestTemplate.exchange("/user", HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });

        Assertions.assertThat(allUsers.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(allUsers.getBody()).isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    @DisplayName("Return list empty when not successful")
    void findAllUsers_ReturnEmptyList_WhenNotSuccessful(){

        ResponseEntity<List<User>> allUsers = testRestTemplate.exchange("/user", HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });

        Assertions.assertThat(allUsers.getBody()).isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("return user by id when successful")
    void findById_ReturnUserByid_WhenSuccessful(){

        User userexpected = CreateUser.savedValid();
        Long idValid = userexpected.getId();

        userRepository.save(userexpected);

        ResponseEntity<User> userResponseEntity = testRestTemplate.getForEntity("/user/{id}", User.class, idValid);

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        Assertions.assertThat(userResponseEntity.getBody()).isEqualTo(userexpected);
    }

    @Test
    @DisplayName("Bad request 400 when not succesful")
    void finById_StatusBadRequest_WhenNotSuccessful(){

        User userexpected = CreateUser.savedValid();
        Long idValid = userexpected.getId();

        ResponseEntity<User> userResponseEntity = testRestTemplate.getForEntity("/user/{id}", User.class, idValid);

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Return list users by name when successful")
    void findByname_ReturnListUsers_WhenSuccessful(){

        User usersaved = CreateUser.saved();

        String nameexpected = usersaved.getName();
        String url = String.format("/user/findByName?name=%s",nameexpected);

        userRepository.save(usersaved);

        ResponseEntity<List<User>> listexpected = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });

        Assertions.assertThat(listexpected.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(listexpected.getBody())
                .isNotEmpty()
                .isNotNull()
                .hasSize(1);
    }

    @Test
    @DisplayName("Return empty list when not successful")
    void findByName_ReturnEmptyList_WhenNotSuccessful(){

        User usersaved = CreateUser.saved();

        String nameexpected = usersaved.getName();
        String url = String.format("/user/findByName?name=%s",nameexpected);

        ResponseEntity<List<User>> listexpected = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });

        Assertions.assertThat(listexpected.getBody())
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Return name by doucument when successful")
    void findByDoucument_ReturnUserByDoucument_WhenSucessful(){

        User usersaved = CreateUser.savedValid();
        String cpfexected = usersaved.getCpf();

        userRepository.save(usersaved);

        String url = String.format("/user/findByCpf?cpf=%s",cpfexected);

        ResponseEntity<User> userbydoucument = testRestTemplate.getForEntity(url, User.class);

        Assertions.assertThat(userbydoucument.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(userbydoucument.getBody()).isEqualTo(usersaved);
    }

    @Test
    @DisplayName("Bad request code 400 when not successful")
    void findByDoucument_ReturnBadRequest_WhenNotSuccessful(){

        User usersaved = CreateUser.savedValid();
        String cpfexected = usersaved.getCpf();

        String url = String.format("/user/findByCpf?cpf=%s",cpfexected);

        ResponseEntity<User> userbydoucument = testRestTemplate.getForEntity(url, User.class);

        Assertions.assertThat(userbydoucument.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
