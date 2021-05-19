package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.Dtos.User.UserDto;
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
    UserService service;


    @GetMapping("/all")
    public List<UserDto> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/delete")
    public void deleteUser(UserDto user) {
        service.deleteUser(user.getUsername());
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable(name = "id") int id) {
        return service.findById(id);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity findByUsername(@PathVariable(name = "username") String username) {
        if (service.checkUsername(username)) {
            return new ResponseEntity("ce nom d'utilisateur est déjà pris !", HttpStatus.FORBIDDEN);
        }else {
            return new ResponseEntity(service.findByUsername(username), HttpStatus.OK);
        }
    }

    @PostMapping("/update")
    public UserDto updateUser(UserDto userDto) throws Exception {
        return service.save(userDto);
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody UserDto user)
    {
        try{
            return new ResponseEntity(service.save(user),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }

}
