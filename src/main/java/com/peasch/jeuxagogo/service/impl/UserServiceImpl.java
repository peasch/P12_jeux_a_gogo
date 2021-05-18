package com.peasch.jeuxagogo.service.impl;

import com.googlecode.jmapper.JMapper;
import com.peasch.jeuxagogo.model.Dtos.User.UserDto;
import com.peasch.jeuxagogo.model.Mappers.UserMapper;
import com.peasch.jeuxagogo.model.entities.User;
import com.peasch.jeuxagogo.repository.UserDao;
import com.peasch.jeuxagogo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;
    @Autowired
    private UserDao dao;
    @Autowired
    private JMapper<UserDto, User> userToDTOMapper;
    @Autowired
    private JMapper<User, UserDto> dtoToUserMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<UserDto> getUsers() {
        List<User> users = dao.findAll();
        return users.stream().map(x -> mapper.fromUserToDto(x)).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDto save(UserDto userDto) {

        return mapper.fromUserToDto(dao.save(mapper.fromDtoToUser(userDto)));

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteUser(String userName) {
        User user = dao.findUserByUsername(userName);
        dao.delete(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public UserDto findById(int id) {
        return mapper.fromUserToDto(dao.findUserById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public UserDto findByUsername(String username) {
        return mapper.fromUserToDto(dao.findUserByUsername(username));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public boolean checkUsername(String username) {
        UserDto user = this.findByUsername(username);
        return (user!=null);
    }



}
