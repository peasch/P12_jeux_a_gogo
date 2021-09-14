package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.dtos.AdviceDto;
import com.peasch.jeuxagogo.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/advice")
public class AdviceController {
    @Autowired
    private AdviceService service;

    @GetMapping("/all")
    public List<AdviceDto> getAdvices(){
        return service.getAdvices();
    }

    @GetMapping("/all/{id}")
    public List<AdviceDto> getAdvicesByGameId(@PathVariable(name = "id")int id){
        return service.getAllAdviceById(id);
    }


    @PostMapping("/add/{id}")
    public ResponseEntity addAdvice(@PathVariable(name = "id")int id,@RequestBody AdviceDto adviceDto){
        try{
            return new ResponseEntity(service.add(adviceDto,id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }
    @GetMapping("/rating/{id}")
    public Float getRAtingOfGameId(@PathVariable(name = "id")int id){
        return service.getRatingOfGameId(id);

    }

    @DeleteMapping("/delete/{id}")
    public void deleteAdvice(@PathVariable(name = "id")int id){
        service.delete(id);
    }
}
