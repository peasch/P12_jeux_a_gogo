package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;


    @GetMapping("/all")
    public List<UserDto> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/delete/{id}")
    public void deleteUser(@PathVariable(name = "id") int id) {
        service.deleteUser(id);
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable(name = "id") int id) {
        return service.findById(id);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity findByUsername(@PathVariable(name = "username") String username) {
        if (service.findByUsername(username) != null) {
            return new ResponseEntity(service.findByUsername(username), HttpStatus.OK);
        } else {
            return new ResponseEntity("cet utilisateur n'existe pas", HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/update")
    public UserDto updateUser(UserDto userDto) throws Exception {
        return service.save(userDto);
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody UserDto user) {
        try {
            return new ResponseEntity(service.save(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }

}
