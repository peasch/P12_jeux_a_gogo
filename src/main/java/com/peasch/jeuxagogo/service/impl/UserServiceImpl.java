package com.peasch.jeuxagogo.service.impl;

import com.googlecode.jmapper.JMapper;
import com.peasch.jeuxagogo.model.Dtos.User.UserDto;
import com.peasch.jeuxagogo.model.entities.User;
import com.peasch.jeuxagogo.repository.UserDao;
import com.peasch.jeuxagogo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao dao;
    @Autowired
    private JMapper<UserDto, User> userToDTOMapper;
    @Autowired
    private JMapper<User, UserDto> dtoToUserMapper;

    public List<UserDto> getUsers() {
        List<User> users = dao.findAll();
        return users.stream().map(x -> userToDTOMapper.getDestination(x)).collect(Collectors.toList());
    }

    public UserDto save(UserDto userDto) {
        return userToDTOMapper.getDestination(dao.save(dtoToUserMapper.getDestination(userDto)));

    }

    public void deleteUser(String userName) {
        User user = dao.findUserByUsername(userName);
        dao.delete(user);
    }

}
