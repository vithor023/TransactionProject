package com.example.transactionbank.service;

import com.example.transactionbank.domain.User;
import com.example.transactionbank.domain.dto.UserDTO;
import com.example.transactionbank.exceptions.BadRequestException;
import com.example.transactionbank.exceptions.BalanceInsuficientException;
import com.example.transactionbank.exceptions.MerchantCannotPaymentException;
import com.example.transactionbank.repository.UserRepository;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private final List<User> list = new ArrayList<>(List.of(CreateUser.saved(),CreateUser.savedSecondUser()));

    private final List<User> listJoao = new ArrayList<>(List.of(CreateUser.saved()));

    @BeforeEach
    void setUp(){

        BDDMockito.when(userRepository.save(ArgumentMatchers.any()))
                .thenReturn(CreateUser.saved());

        BDDMockito.when(userRepository.findAll())
                .thenReturn(list);

        BDDMockito.when(userRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(CreateUser.savedValid()));

        BDDMockito.when(userRepository.searchLikeByName(ArgumentMatchers.anyString()))
                .thenReturn(listJoao);

        BDDMockito.when(userRepository.searchByDoucument(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(CreateUser.savedValid()));
    }

    @Test
    @DisplayName("Return user when successful")
    void user_returnUserSaved_WhenSuccessful(){

        UserDTO userDTOSaved = CreateDto.userDTO(CreateUser.saved());

        User usersaved = this.userService.saveUser(userDTOSaved);

        User userexpected = CreateUser.saved();

        Assertions.assertThat(usersaved)
                .isNotNull()
                .isEqualTo(userexpected);

        Assertions.assertThat(usersaved.getName()).isEqualTo(userexpected.getName());
        Assertions.assertThat(usersaved.getUserType()).isEqualTo(userexpected.getUserType());
        Assertions.assertThat(usersaved.getCpf()).isEqualTo(userexpected.getCpf());
        Assertions.assertThat(usersaved.getEmail()).isEqualTo(userexpected.getEmail());
        Assertions.assertThat(usersaved.getBalance()).isEqualTo(userexpected.getBalance());
        Assertions.assertThat(usersaved.getPasswod()).isEqualTo(userexpected.getPasswod());
    }

    @Test
    @DisplayName("Return List users when successful")
    void listUser_ReturnListUser_WhenSuccessful(){

        List<User> userssaved = userService.findAll();

        Assertions.assertThat(userssaved).isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    @DisplayName("Return list empty when nothing user is saved")
    void findAll_returnListEmpty_whenNoContainsUsers(){

        List<User> listempty = new ArrayList<>();

        BDDMockito.when(userRepository.findAll())
                .thenReturn(listempty);

        List<User> userssaved = userService.findAll();

        Assertions.assertThat(userssaved).isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Return userById when successful")
    void findById_ReturnUser_WhenSuccesful(){

        User usersaved = CreateUser.savedValid();
        Long idexected = usersaved.getId();

        User userbyid = userService.findById(idexected);

        Assertions.assertThat(userbyid).isNotNull()
                .isEqualTo(usersaved);
    }

    @Test
    @DisplayName("Return throw BadrequestException when not successful")
    void findById_ReturnBadRequestException_WhenNotSucessful(){

        BDDMockito.when(userRepository.findById(ArgumentMatchers.anyLong()))
                .thenThrow(BadRequestException.class);

        Long idnotexist = CreateUser.savedSecondValidUser().getId();

        Assertions.assertThatThrownBy(() -> userService.findById(idnotexist))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Return true when successful")
    void userValidate_ReturnTrue_WhenSuccessful(){

        User userValid = CreateUser.saved();

        BigDecimal transaction = BigDecimal.valueOf(120);

        boolean isvalid = userService.userValidate(userValid, transaction);

        Assertions.assertThat(isvalid).isTrue();
    }

    @Test
    @DisplayName("Return throw BalanceInsuficientException when not successful")
    void userValid_ReturnBalanceInsuficientException_whenNotSuccessful(){

        User userValid = CreateUser.saved();

        BigDecimal transaction = BigDecimal.valueOf(125);

        Assertions.assertThatThrownBy(() -> userService.userValidate(userValid,transaction))
                .isInstanceOf(BalanceInsuficientException.class);
    }

    @Test
    @DisplayName("Return MerchantCannotPaymentException when not successful")
    void userValid_MerchantCannotPaymentException_whenNotSuccessful(){

        User userValid = CreateUser.savedSecondUser();

        BigDecimal transaction = BigDecimal.valueOf(543.09);

        Assertions.assertThatThrownBy(() -> userService.userValidate(userValid,transaction))
                .isInstanceOf(MerchantCannotPaymentException.class);
    }

    @Test
    @DisplayName("Return list users by name when successful")
    void findLikeName_ReturnListUsers_WhenSuccessful(){

        List<User> likeName = userService.findLikeName("joao");

        Assertions.assertThat(likeName)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    @DisplayName("Return empty list when not successful")
    void findLikeName_ReturnEmptyList_whenNotSuccessful(){
        List<User> listempty = new ArrayList<>();

        BDDMockito.when(userRepository.searchLikeByName(ArgumentMatchers.anyString()))
                .thenReturn(listempty);

        List<User> userssaved = userService.findLikeName("Joao");

        Assertions.assertThat(userssaved).isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Return user by doucument when successful")
    void findByDoucument_ReturnUserByDoucument_WhenSuccessful(){

        User usersaved = CreateUser.savedValid();

        User userexpected = userService.findByDoucument(usersaved.getCpf());

        Assertions.assertThat(userexpected)
                .isNotNull()
                .isEqualTo(usersaved);
    }

    @Test
    @DisplayName("Throw BadRequestException when not successful")
    void findByDoucument_ThrowBadRequestException_WhenNotSuccesful(){

        BDDMockito.when(userRepository.searchByDoucument(ArgumentMatchers.anyString()))
                .thenThrow(BadRequestException.class);

        String cpfnotexists = CreateUser.savedSecondValidUser().getCpf();

        Assertions.assertThatThrownBy(() -> userService.findByDoucument(cpfnotexists))
                .isInstanceOf(BadRequestException.class);
    }
}