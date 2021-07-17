package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.controller.security.config.JwtTokenProvider;
import com.peasch.jeuxagogo.controller.security.service.CustomUserDetailsService;
import com.peasch.jeuxagogo.model.dtos.RoleDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.service.RoleService;
import com.peasch.jeuxagogo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
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
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDto user)  {
        Set<RoleDto> roles = new HashSet<>();
        roles.add(roleService.findByRole("USER"));
        user.setRolesDto(roles);
        if (userService.findByUsername(user.getUsername()) != null) {
            throw new BadCredentialsException("User with username: " + user.getUsername() + " already exists");
        }
        customUserDetailsService.saveUser(user);
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ok(model);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDto user){
        try {
            String userName = user.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, user.getPassword()));

            return new ResponseEntity(jwtTokenProvider.createToken(userName, userService.findByUsernameWithRoles(userName).getRolesDto()), HttpStatus.OK);


        } catch (BadCredentialsException e) {
            return new ResponseEntity("Invalid Username/password", HttpStatus.BAD_REQUEST);
        }

    }



}
