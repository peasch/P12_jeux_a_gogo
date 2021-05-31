package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.dtos.EditorDto;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.service.EditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public ResponseEntity addEditor(@RequestBody EditorDto editor)
    {
        try{
            return new ResponseEntity(service.save(editor), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }

    @PutMapping("/update")
    public ResponseEntity updateGame(@RequestBody EditorDto editor)
    {
        try{
            return new ResponseEntity(service.update(editor), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteGame(@PathVariable(name = "id")int id)
    {
        try{
            service.delete(id);
            return new ResponseEntity( HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }
}
