package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.RoleMapper;
import com.peasch.jeuxagogo.model.dtos.RoleDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.model.Mappers.UserMapper;
import com.peasch.jeuxagogo.model.entities.Role;
import com.peasch.jeuxagogo.model.entities.User;
import com.peasch.jeuxagogo.repository.RoleDao;
import com.peasch.jeuxagogo.repository.UserDao;
import com.peasch.jeuxagogo.service.RoleService;
import com.peasch.jeuxagogo.service.misc.Text_FR;
import com.peasch.jeuxagogo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;
    @Autowired
    private UserDao dao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleService roleService;


    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<UserDto> getUsers() {
        return dao.findAll().stream().map(mapper::fromUserToDtoWithrole)
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDto save(UserDto userDto) {
        this.validationOfUser(userDto);
        UserDto userDto2 = mapper.fromUserToDtoWithrole(dao.save(mapper.fromDtoToUser(userDto)));
        for (RoleDto role: userDto.getRolesDto()){
            Role roleUpdate = roleDao.findByRole(role.getRole());
            Set<User> users = roleUpdate.getUsers();
            users.add(mapper.fromDtoToUser(userDto));
            roleUpdate.setUsers(users);
            roleDao.save(roleUpdate);
        }
        return userDto2;
    }
    public UserDto saveWithRole(UserDto userDto) {
        User user = mapper.fromDtoToUser(userDto);
        Set<RoleDto> rolesDto = userDto.getRolesDto();
        Set<Role> roles = rolesDto.stream().map(roleMapper::fromDtoToRole).collect(Collectors.toSet());
        user.setRoles(roles);
        dao.save(user);
        for (Role role : roles) {
            Role roleUpdate = roleDao.findByRole(role.getRole());
            Set<User> users = roleUpdate.getUsers();
            users.add(user);
            roleUpdate.setUsers(users);
            roleDao.save(roleUpdate);
        }
        return mapper.fromUserToDtoWithrole(user);
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
        user.setAdvices(userToUpdate.getAdvices());
        CustomConstraintValidation<UserDto> customConstraintValidation = new CustomConstraintValidation<>();
        customConstraintValidation.validate(user);

        return mapper.fromUserToStrictDto(dao.save(mapper.fromDtoToUser(user)));
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteUser(int id) {
        User user = dao.findUserById(id);
        Set<Role> roles = user.getRoles();
        for (Role role:roles){
            roles.remove(role);
        }
        dao.save(user);
        dao.delete(user);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDto addRoleToUser(UserDto user, int id){
        UserDto userDto = mapper.fromUserToDtoWithrole(dao.findUserById(user.getId()));
        Set<RoleDto> roles = userDto.getRolesDto();
        roles.add(roleService.findById(id));
        userDto.setRolesDto(roles);
       return this.update(userDto);
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
    public UserDto findByUsernameWithRoles(String username) {
        return mapper.fromUserToDtoWithrole((dao.findUserByUsername(username)));
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

    public void validationOfBorrower(UserDto borrower) throws ValidationException{
        if (!this.checkUsername(borrower.getUsername())) {
            throw new ValidationException(Text_FR.INVALID_USER);

        }
    }

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
