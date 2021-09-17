package com.peasch.jeuxagogo.controller;
import com.peasch.jeuxagogo.model.dtos.GameStyleDto;
import com.peasch.jeuxagogo.service.GameStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/style")
public class GameStyleController {
    @Autowired
    private GameStyleService service;

    @GetMapping("/all")
    public List<GameStyleDto> getGameStyles() {
        return service.getGameStyles();
    }

    @GetMapping("/{id}")
    public GameStyleDto getStyleById(@PathVariable(name = "id")int id){
        return service.findById(id);
    }

    @PostMapping("/add")
    public ResponseEntity addGameStyle(@RequestBody GameStyleDto gameStyleDto){
        try{
            return new ResponseEntity(service.save(gameStyleDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteGameStyle(@PathVariable(name = "id")int id){
        service.delete(id);
    }
}
