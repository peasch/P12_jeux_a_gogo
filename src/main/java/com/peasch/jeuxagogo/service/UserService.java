package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.UserDto;

import java.util.List;


public interface UserService {

    List<UserDto> getUsers();

    UserDto save(UserDto userDto) throws Exception;

    void deleteUser(int id);

    UserDto findById(int id);

    UserDto findByUsername(String username);
//--------------------------------------
}
