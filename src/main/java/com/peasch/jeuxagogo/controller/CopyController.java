package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.dtos.CopyDto;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.service.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/copy")
public class CopyController {
    @Autowired
    private CopyService service;

    @GetMapping("/all")
    public List<CopyDto> getAllCopies(){
        return service.getAll();
    }

    @GetMapping("/game/{id}")
    public List<CopyDto> getAllCopiesByGameId(@PathVariable(name = "id") int id){
        return service.getCopiesByGameId(id);
    }

    @PostMapping("/add/{id}")
    public ResponseEntity addCopy(@PathVariable(name = "id")int id,@RequestBody CopyDto copyDto)
    {
        try{
            return new ResponseEntity(service.save(copyDto,id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }
}
