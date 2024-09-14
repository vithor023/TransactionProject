package com.example.transactionbank.controller.endpoints;

import com.example.transactionbank.domain.User;
import com.example.transactionbank.domain.dto.UserDTO;
import com.example.transactionbank.exceptions.BadRequestException;
import com.example.transactionbank.service.UserService;
import com.example.transactionbank.util.CreateDto;
import com.example.transactionbank.util.CreateUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private final List<User> list = new ArrayList<>(List.of(CreateUser.saved(),CreateUser.savedSecondUser()));

    private final List<User> listJoao = new ArrayList<>(List.of(CreateUser.saved()));

    @BeforeEach
    void setUp(){

        BDDMockito.when(userService.saveUser(ArgumentMatchers.any(UserDTO.class)))
                .thenReturn(CreateUser.savedValid());

        BDDMockito.when(userService.findAll())
                .thenReturn(list);

        BDDMockito.when(userService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(CreateUser.savedValid());

        BDDMockito.when(userService.findLikeName(ArgumentMatchers.anyString()))
                .thenReturn(listJoao);

        BDDMockito.when(userService.findByDoucument(ArgumentMatchers.anyString()))
                .thenReturn(CreateUser.savedValid());
    }

    @Test
    @DisplayName("Return user saved when successful")
    void saveUser_ReturnSavedUser_WhenSuccessful(){

        UserDTO userdtosaved = CreateDto.userDTO(CreateUser.saved());

        ResponseEntity<User> userResponseEntity = userController.saveUser(userdtosaved);
        User userexpected = CreateUser.savedValid();

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(userResponseEntity.getBody()).isEqualTo(userexpected);
    }

    @Test
    @DisplayName("Return list users when successful")
    void findAllusers_ReturnListusers_whenSuccessful(){

        ResponseEntity<List<User>> allUsers = userController.findAllUsers();

        Assertions.assertThat(allUsers.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(allUsers.getBody()).isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    @DisplayName("Return list empty when not successful")
    void findAllUsers_ReturnEmptyList_WhenNotSuccessful(){

        List<User> userList = new ArrayList<>();

        BDDMockito.when(userService.findAll())
                .thenReturn(userList);

        ResponseEntity<List<User>> allUsers = userController.findAllUsers();

        Assertions.assertThat(allUsers.getBody()).isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("return user by id when successful")
    void findById_ReturnUserByid_WhenSuccessful(){

        User userexpected = CreateUser.savedValid();
        Long idValid = userexpected.getId();

        ResponseEntity<User> userResponseEntity = userController.finById(idValid);

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        Assertions.assertThat(userResponseEntity.getBody()).isEqualTo(userexpected);
    }

    @Test
    @DisplayName("Throw BadrequestException when not successful")
    void findById_ThrowBadrequestException_WhenNotSuccessful(){

        User userexpected = CreateUser.savedValid();
        Long idValid = userexpected.getId();

        BDDMockito.when(userService.findById(ArgumentMatchers.anyLong()))
                .thenThrow(BadRequestException.class);

        Assertions.assertThatThrownBy(() -> userController.finById(idValid))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Return list users by name when successful")
    void findByname_ReturnListUsers_WhenSuccessful(){

        ResponseEntity<List<User>> listexpected = userController.findByName("Joao");

        Assertions.assertThat(listexpected.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(listexpected.getBody())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    @DisplayName("Return empty list when not successful")
    void findByName_ReturnEmptyList_WhenNotSuccessful(){

        List<User> list1 = new ArrayList<>();

        BDDMockito.when(userService.findLikeName(ArgumentMatchers.anyString()))
                .thenReturn(list1);

        ResponseEntity<List<User>> listempty = userController.findByName("Joao");

        Assertions.assertThat(listempty.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(listempty.getBody())
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Return name by doucument when successful")
    void findByDoucument_ReturnUserByDoucument_WhenNSucessful(){

        User usersaved = CreateUser.savedValid();

        ResponseEntity<User> userbydoucument = userController.findByDoucument(usersaved.getCpf());

        Assertions.assertThat(userbydoucument.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(userbydoucument.getBody()).isEqualTo(usersaved);
    }

    @Test
    @DisplayName("Throw BadrequestException when not successful")
    void findByDoucument_ThrowBadRequestException_WhenNotSuccess(){

        User userexpected = CreateUser.savedValid();
        String cpfValid = userexpected.getCpf();

        BDDMockito.when(userService.findByDoucument(ArgumentMatchers.anyString()))
                .thenThrow(BadRequestException.class);

        Assertions.assertThatThrownBy(() -> userController.findByDoucument(cpfValid))
                .isInstanceOf(BadRequestException.class);
    }

}