package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.controller.security.service.CustomUserDetailsService;
import com.peasch.jeuxagogo.mailing.EmailService;
import com.peasch.jeuxagogo.model.Mappers.RoleMapper;
import com.peasch.jeuxagogo.model.Mappers.UserMapper;
import com.peasch.jeuxagogo.model.dtos.RoleDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.model.entities.Role;
import com.peasch.jeuxagogo.model.entities.User;
import com.peasch.jeuxagogo.repository.RoleDao;
import com.peasch.jeuxagogo.repository.UserDao;
import com.peasch.jeuxagogo.service.BorrowingService;
import com.peasch.jeuxagogo.service.RoleService;
import com.peasch.jeuxagogo.service.UserService;
import com.peasch.jeuxagogo.service.misc.Text_FR;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.validation.ValidationException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final static String RESETMAILSUBJECT = "Réinitialiser le mot de passe Jeux à gogo.";
    private final static String RESETMAILMESSAGE = "Cliquez sur le lien pour réinitialiser votre mot de passe : " +
            "http://localhost:4200/resetPassword?token=%s";

    @Autowired
    private UserMapper mapper;
    @Autowired
    private UserDao dao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;


    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<UserDto> getUsers() {
        return dao.findAll().stream().map(mapper::fromUserToDtoWithrole)
                .sorted(Comparator.comparing(o -> o.getName().toLowerCase())).collect(Collectors.toList());

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDto getUserByUsername(String username) {
        return this.setUserFree(username);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    UserDto setUserFree(String username) {
        UserDto user = this.findByUsername(username);
        boolean free = borrowingService.getUnreturnedBorrowingsByUsername(username).isEmpty();
        user.setFree(free);
        return mapper.fromUserToDtoWithrole(dao.save(mapper.fromDtoToUser(user)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDto save(UserDto userDto) {
        this.validationOfUser(userDto);
        UserDto userDto2 = mapper.fromUserToDtoWithrole(dao.save(mapper.fromDtoToUser(userDto)));
        for (RoleDto role : userDto.getRolesDto()) {
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

    public UserDto saveWithRoleWithNewPassword(UserDto userDto) {
        this.validationOfUser(userDto);
        User user = mapper.fromDtoToUser(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
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
    public UserDto update(UserDto userToUpdate) {
        UserDto user = this.findByIdWithRole(userToUpdate.getId());
        user.setAdhesionDate(userToUpdate.getAdhesionDate());
        user.setBirthDate(userToUpdate.getBirthDate());
        user.setEmail(userToUpdate.getEmail());
        user.setFirstname(userToUpdate.getFirstname());
        user.setName(userToUpdate.getName());
        user.setUsername(userToUpdate.getUsername());
        user.setAdvices(userToUpdate.getAdvices());
        user.setRolesDto(userToUpdate.getRolesDto());
        user.setResetPassword(userToUpdate.getResetPassword());
        CustomConstraintValidation<UserDto> customConstraintValidation = new CustomConstraintValidation<>();
        customConstraintValidation.validate(user);

        return mapper.fromUserToDtoWithrole(dao.save(mapper.fromDtoToUser(user)));
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteUser(int id) throws Exception {
        var user = dao.findUserById(id);
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            roles.remove(role);
        }
        dao.save(user);
        dao.delete(user);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDto addRoleToUser(RoleDto roleDto, int id) {
        UserDto userDto = mapper.fromUserToDtoWithrole(dao.findUserById(id));
        Set<RoleDto> roles = userDto.getRolesDto();
        roles.add(roleService.findById(roleDto.getId()));
        userDto.setRolesDto(roles);
        return this.saveWithRole(userDto);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDto removeRoleOfUser(UserDto user, int id) {
        UserDto userDto = mapper.fromUserToDtoWithrole(dao.findUserById(user.getId()));
        Set<RoleDto> roles = userDto.getRolesDto();
        RoleDto roleToRemove = roleService.findById(id);
        if (roles.contains(roleToRemove)) {
            roles.remove(roleToRemove);
        }

        userDto.setRolesDto(roles);

        return this.update(userDto);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendMailToReset(String username) throws MessagingException {
        var user = this.findByUsername(username);
        var resetPassword = RandomStringUtils.randomAlphabetic(12);
        user.setResetPassword(resetPassword);
        dao.save(mapper.fromDtoToUser(user));
        emailService.send(user.getEmail(), RESETMAILSUBJECT, String.format(RESETMAILMESSAGE, resetPassword));

    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDto resetPassword(String token, String password) {
        var userDto = this.findByToken(token);
        userDto.setPassword(bCryptPasswordEncoder.encode(password));
        userDto.setResetPassword(null);
        return mapper.fromUserToDtoWithrole(dao.save(mapper.fromDtoToUser(userDto)));

    }


    //------------------ FINDING USER -----------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public UserDto findById(int id) {
        return mapper.fromUserToDtoWithrole(dao.findUserById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDto findByToken(String token) {
        return mapper.fromUserToDtoWithrole(dao.findUserByResetPassword(token));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDto findByIdWithRole(int id) {
        return mapper.fromUserToDtoWithrole(dao.findUserById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDto findByUsername(String username) {
        return mapper.fromUserToDtoWithrole(dao.findUserByUsername(username));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public UserDto findByUsernameWithRoles(String username) {
        return mapper.fromUserToDtoWithrole((dao.findUserByUsername(username)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public UserDto findByEmail(String email) {
        return mapper.fromUserToDtoWithrole(dao.findUserByEmail(email));
    }

    //------------------------------checking fields ------------------------


    private boolean checkUsername(String username) {
        return this.findByUsername(username) != null;

    }


    private boolean checkEmail(String email) {
        return this.findByEmail(email) != null;

    }

    //-------------------------------------Validations------------------------------------

    public void validationOfBorrower(UserDto borrower) throws ValidationException {
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
