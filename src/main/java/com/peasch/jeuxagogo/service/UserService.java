package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.RoleDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;

import javax.mail.MessagingException;
import java.util.List;


public interface UserService {

    List<UserDto> getUsers();

    UserDto save(UserDto userDto) throws Exception;

    void deleteUser(int id) throws Exception;

    UserDto findById(int id);

    UserDto findByUsername(String username);

    UserDto addRoleToUser(RoleDto roleDto, int id);

    UserDto saveWithRole(UserDto userDto);

    UserDto saveWithRoleWithNewPassword(UserDto userDto);

    UserDto resetPassword(String token, String password);

    void sendMailToReset(String username) throws MessagingException;


    UserDto findByUsernameWithRoles(String username);

    UserDto findByIdWithRole(int id);

    UserDto findByEmail(String email);

    UserDto findByToken(String token);

    UserDto removeRoleOfUser(UserDto user, int id);

    UserDto getUserByUsername(String username);

    UserDto update(UserDto userToUpdate);

    void validationOfBorrower(UserDto borrower);
}
