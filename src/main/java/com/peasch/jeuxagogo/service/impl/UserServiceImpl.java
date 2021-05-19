package com.peasch.jeuxagogo.service.impl;

import com.googlecode.jmapper.JMapper;
import com.peasch.jeuxagogo.model.Dtos.User.UserDto;
import com.peasch.jeuxagogo.model.Mappers.UserMapper;
import com.peasch.jeuxagogo.model.entities.User;
import com.peasch.jeuxagogo.repository.UserDao;
import com.peasch.jeuxagogo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;
    @Autowired
    private UserDao dao;


    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<UserDto> getUsers() {
        List<User> users = dao.findAll();
        return users.stream().map(x -> mapper.fromUserToDto(x)).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDto save(UserDto userDto) throws Exception {
        if (this.checkUsername(userDto.getUsername())){
            throw new Exception("le nom d'utilisateur est déjà pris");

        }
        if (this.checkEmail(userDto.getEmail())){
            throw  new Exception("cet email est déjà utilisé");
        }
        return mapper.fromUserToDto(dao.save(mapper.fromDtoToUser(userDto)));

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteUser(String userName) {
        User user = dao.findUserByUsername(userName);
        dao.delete(user);
    }

    //------------------ FINDING USER -----------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public UserDto findById(int id) {
        return mapper.fromUserToDto(dao.findUserById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public UserDto findByUsername(String username) {
        return mapper.fromUserToDto(dao.findUserByUsername(username));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public UserDto findByEmail(String email) {
        return mapper.fromUserToDto(dao.findUserByUsername(email));
    }

    //------------------------------checking fields ------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public boolean checkUsername(String username) {
        UserDto user = this.findByUsername(username);
        return (user != null);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public boolean checkEmail(String email) {
        UserDto user =this.findByEmail(email);
        return (user !=null);

    }

}
