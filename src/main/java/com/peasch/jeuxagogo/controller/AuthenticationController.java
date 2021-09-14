package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.controller.security.config.JwtTokenProvider;
import com.peasch.jeuxagogo.mailing.EmailService;
import com.peasch.jeuxagogo.model.dtos.RoleDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.service.RoleService;
import com.peasch.jeuxagogo.service.UserService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private EmailService emailService;

    private static final String BIENVENUE_SUBJECT = "Bienvenue sur Jeuxagogo.fr";
    private static final String BIENVENUE_MESSAGE = "Bienvenue sur Jeuxagogo.fr, vous pouvez dès à présent profiter du site." +
            "Si vous avez une adhésion en cours, vous allez avoir le statut de membre pour pouvoir emprunter des jeux. Sinon, et bien adhérez :)";
    private static final String ADMIN = "peaschaming@gmail.com";
    private static final String ADMIN_MESSAGE = "Un utilisateur a tenté de s'enregistrer avec un mail invalide: %s. " +
            "erreur : %s";

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDto user) {

        if (userService.findByEmail(user.getEmail()) == null) {

            try {
                Set<RoleDto> roles = new HashSet<>();
                roles.add(roleService.findByRole("USER"));
                user.setRolesDto(roles);
                userService.saveWithRoleWithNewPassword(user);
                Map<Object, Object> model = new HashMap<>();

                model.put("message", "User registered successfully");
                return ok(model);
            } catch (ValidationException e) {
                return new ResponseEntity("Ce nom d'utilisateur est déjà utilisé", HttpStatus.FORBIDDEN);
            }

        } else {
            return new ResponseEntity("Cette adresse mail est déjà utilisé", HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDto user) {
        try {
            String userName = user.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, user.getPassword()));

            return new ResponseEntity(jwtTokenProvider.createToken(userName, userService.findByUsernameWithRoles(userName).getRolesDto()), HttpStatus.OK);


        } catch (BadCredentialsException e) {
            return new ResponseEntity("Invalid Username/password", HttpStatus.BAD_REQUEST);
        }

    }


}
