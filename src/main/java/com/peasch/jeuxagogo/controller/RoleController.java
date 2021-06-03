package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.dtos.RoleDto;
import com.peasch.jeuxagogo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService service;

    @GetMapping("/all")
    public List<RoleDto> getRoles(){
        return service.getRoles();
    }
    @GetMapping("/{id}")
    public RoleDto getRoleById(@PathVariable(name = "id")int id){
        return service.findById(id);
    }

}
