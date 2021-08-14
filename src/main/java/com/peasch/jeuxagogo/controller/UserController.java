package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.dtos.RoleDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;


    @GetMapping("/all")
    public List<UserDto> getUsers() {
        return service.getUsers();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable(name = "id") int id) {
        try {
            service.deleteUser(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity("l'utilisateur a encore des emprunts en cours",HttpStatus.FORBIDDEN);
        }

    }

    @DeleteMapping("/delete/username/{username}")
    public ResponseEntity deleteUser(@PathVariable(name = "username") String username) {
        try {
            service.deleteUser(service.findByUsername(username).getId());
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity("l'utilisateur a encore des emprunts en cours",HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable(name = "id") int id) {
        UserDto user = service.findByIdWithRole(id);
        return service.getUserByUsername(user.getUsername());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity findByUsername(@PathVariable(name = "username") String username) {
        if (service.findByUsername(username) != null) {
            return new ResponseEntity(service.getUserByUsername(username),HttpStatus.OK );
        } else {
            return new ResponseEntity("cet utilisateur n'existe pas", HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/update")
    public UserDto updateUser(@RequestBody UserDto userDto) throws Exception {
        return service.update(userDto);
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody UserDto user) {
        try {
            return new ResponseEntity(service.save(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }
    @PutMapping("/addRole/{id}")
    public ResponseEntity addRoleToUser(@PathVariable(name = "id") int id, @RequestBody RoleDto roleDto) {
        try {
            return new ResponseEntity(service.addRoleToUser(roleDto,id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }

    @PutMapping("/removeRole/{id}")
    public ResponseEntity removeRoleToUser(@PathVariable(name = "id") int id, @RequestBody UserDto userDto) {
        try {
            return new ResponseEntity(service.removeRoleOfUser(userDto,id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }
    @GetMapping("/userWithRoles/{username}")
    public ResponseEntity getRolesOfUser(@PathVariable(name = "username")String username){
        if (service.findByUsername(username) != null) {
            return new ResponseEntity(service.findByUsernameWithRoles(username),HttpStatus.OK);
        } else {
            return new ResponseEntity("cet utilisateur n'existe pas", HttpStatus.NOT_FOUND);
        }
    }
}
