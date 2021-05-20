package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Dtos.User.UserDto;
import com.peasch.jeuxagogo.model.Mappers.UserMapper;
import com.peasch.jeuxagogo.model.entities.User;
import com.peasch.jeuxagogo.repository.UserDao;
import com.peasch.jeuxagogo.service.UserService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Log
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;
    @Autowired
    private UserDao dao;

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<UserDto> getUsers() {
        List<User> users = dao.findAll();
        return users.stream().map(x -> mapper.fromUserToDto(x)).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDto save(UserDto userDto) throws Exception {
        this.validationOfUser(userDto);
        return mapper.fromUserToDto(dao.save(mapper.fromDtoToUser(userDto)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteUser(int id) {
        User user = dao.findUserById(id);
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
        return mapper.fromUserToDto(dao.findUserByEmail(email));
    }

    //------------------------------checking fields ------------------------


    private boolean checkUsername(String username) {
        return this.findByUsername(username)!= null;

    }


    private boolean checkEmail(String email) {
        return this.findByEmail(email)!= null;

    }

    private void validationOfUser(UserDto user) throws Exception {
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(user);
        if (this.checkUsername(user.getUsername())) {
            throw new Exception("le nom d'utilisateur est déjà pris");

        }
        if (this.checkEmail(user.getEmail())) {
            throw new Exception("cet email est déjà utilisé");
        }
        if (!constraintViolations.isEmpty()) {
            System.out.println("Impossible de valider les informations de l'utilisateur : ");
            for (ConstraintViolation<UserDto> contraintes : constraintViolations) {
                System.out.println(contraintes.getRootBeanClass().getSimpleName() +
                        "." + contraintes.getPropertyPath() + " " + contraintes.getMessage());
            }
            throw new Exception("les informations sont incorrectes");
        }
    }
}
