package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.model.Mappers.UserMapper;
import com.peasch.jeuxagogo.model.entities.User;
import com.peasch.jeuxagogo.repository.UserDao;
import com.peasch.jeuxagogo.service.Text;
import com.peasch.jeuxagogo.service.UserService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;
    @Autowired
    private UserDao dao;

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<UserDto> getUsers() {
        return dao.findAll().stream().map(mapper::fromUserToStrictDto)
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDto save(UserDto userDto) throws Exception {
        this.validationOfUser(userDto);
        return mapper.fromUserToStrictDto(dao.save(mapper.fromDtoToUser(userDto)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteUser(int id) {
        User user = dao.findUserById(id);
        dao.delete(user);
    }

    //------------------ FINDING USER -----------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public UserDto findById(int id) {
        return mapper.fromUserToStrictDto(dao.findUserById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public UserDto findByUsername(String username) {
        return mapper.fromUserToStrictDto(dao.findUserByUsername(username));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public UserDto findByEmail(String email) {
        return mapper.fromUserToStrictDto(dao.findUserByEmail(email));
    }

    //------------------------------checking fields ------------------------


    private boolean checkUsername(String username) {
        return this.findByUsername(username)!= null;

    }


    private boolean checkEmail(String email) {
        return this.findByEmail(email)!= null;

    }

    private void validationOfUser(UserDto user) throws ValidationException {
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(user);
        if (this.checkUsername(user.getUsername())) {
            throw new ValidationException(Text.ALREADY_USED_USERNAME);

        }
        if (this.checkEmail(user.getEmail())) {
            throw new ValidationException(Text.ALREADY_USED_EMAIL);
        }
        if (!constraintViolations.isEmpty()) {
            System.out.println(Text.INVALID_USER);

            for (ConstraintViolation<UserDto> contraintes : constraintViolations) {
                System.out.println(contraintes.getRootBeanClass().getSimpleName() +
                        "." + contraintes.getPropertyPath() + " " + contraintes.getMessage());
            }
            throw new ValidationException(Text.INCORRECT_INFORMATION);
        }
    }
}
