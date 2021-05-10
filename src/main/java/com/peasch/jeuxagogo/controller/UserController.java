package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.Dtos.User.UserDto;
import com.peasch.jeuxagogo.repository.UserDao;
import com.peasch.jeuxagogo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;


    @GetMapping("all")
    public List<UserDto> getUsers() {
        return service.getUsers();
    }

    @GetMapping("delete")
    public void deleteUser(UserDto user){
       service.deleteUser(user.getUsername());
    }

}
