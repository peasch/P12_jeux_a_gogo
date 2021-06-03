package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.dtos.EditorDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.model.Mappers.UserMapper;
import com.peasch.jeuxagogo.model.entities.User;
import com.peasch.jeuxagogo.repository.UserDao;
import com.peasch.jeuxagogo.service.misc.Text_FR;
import com.peasch.jeuxagogo.service.UserService;
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
    public UserDto update (UserDto userToUpdate){
        UserDto user = this.findById(userToUpdate.getId());
        user.setAdhesionDate(userToUpdate.getAdhesionDate());
        user.setBirthDate(userToUpdate.getBirthDate());
        user.setEmail(userToUpdate.getEmail());
        user.setFirstname(userToUpdate.getFirstname());
        user.setName(userToUpdate.getName());
        user.setUsername(userToUpdate.getUsername());
        CustomConstraintValidation<UserDto> customConstraintValidation = new CustomConstraintValidation<>();
        customConstraintValidation.validate(user);

        return mapper.fromUserToStrictDto(dao.save(mapper.fromDtoToUser(user)));
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

    //-------------------------------------Validations------------------------------------

    private void validationOfUser(UserDto user) throws ValidationException {
        if (this.checkUsername(user.getUsername())) {
            throw new ValidationException(Text_FR.ALREADY_USED_USERNAME);

        }
        if (this.checkEmail(user.getEmail())) {
            throw new ValidationException(Text_FR.ALREADY_USED_EMAIL);
        }
        CustomConstraintValidation<UserDto> customConstraintValidation = new CustomConstraintValidation<>();
        customConstraintValidation.validate(user);
    }
}
