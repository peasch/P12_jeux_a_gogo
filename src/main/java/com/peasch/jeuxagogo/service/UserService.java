package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.RoleDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;

import java.util.List;


public interface UserService {

    List<UserDto> getUsers();

    UserDto save(UserDto userDto) throws Exception;

    void deleteUser(int id) throws Exception;

    UserDto findById(int id);

    UserDto findByUsername(String username);

    UserDto addRoleToUser(RoleDto roleDto, int id);

    UserDto saveWithRole(UserDto userDto);

    UserDto findByUsernameWithRoles(String username);
    UserDto findByIdWithRole(int id);
    UserDto removeRoleOfUser(UserDto user,int id);
    UserDto getUserByUsername(String username);
    UserDto update (UserDto userToUpdate);
    void validationOfBorrower(UserDto borrower);
}
