package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.dtos.EditorDto;
import com.peasch.jeuxagogo.service.EditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/editor")
public class EditorController {

    @Autowired
    private EditorService service;

    @GetMapping("all")
    public List<EditorDto> getEditors(){
        return service.getEditors();
    }

    @GetMapping("{id}")
    public EditorDto getEditorById(@PathVariable(name = "id")int id){
        return service.findById(id);
    }
}
