package com.example.transactionbank.controller.endpoints;

import com.example.transactionbank.domain.User;
import com.example.transactionbank.domain.dto.UserDTO;
import com.example.transactionbank.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@Valid @RequestBody UserDTO userDTO){

        User usersaved = userService.saveUser(userDTO);
        return new ResponseEntity<>(usersaved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers(){

        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> finById(@PathVariable Long id){

        User user = userService.findById(id);
        return new ResponseEntity<>(user,HttpStatus.FOUND);
    }

    @GetMapping("/findByName")
    public ResponseEntity<List<User>> findByName(@RequestParam(required = false) String name){

        List<User> likeName = userService.findLikeName(name);
        return new ResponseEntity<>(likeName,HttpStatus.OK);
    }

    @GetMapping("/findByCpf")
    public ResponseEntity<User> findByDoucument(@RequestParam(required = false) String cpf){

        User byDoucument = userService.findByDoucument(cpf);
        return new ResponseEntity<>(byDoucument,HttpStatus.OK);
    }
}
