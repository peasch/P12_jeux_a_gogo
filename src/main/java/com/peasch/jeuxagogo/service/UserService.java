package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.Dtos.User.UserDto;
import com.peasch.jeuxagogo.model.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


public interface UserService {

     List<UserDto> getUsers();

     UserDto save(UserDto userDto);
    void deleteUser(String userName);
}
