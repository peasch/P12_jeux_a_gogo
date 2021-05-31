package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.dtos.CopiesDto;
import com.peasch.jeuxagogo.service.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/copy")
public class CopyController {
    @Autowired
    private CopyService service;

    @GetMapping("/all")
    public List<CopiesDto> getAllCopies(){
        return service.getAll();
    }

    @GetMapping("/game/{id}")
    public List<CopiesDto> getAllCopiesByGameId(@PathVariable(name = "id") int id){
        return service.getCopiesByGameId(id);
    }
}
