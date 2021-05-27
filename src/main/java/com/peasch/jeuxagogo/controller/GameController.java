package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService service;

    @GetMapping("/all")
    public List<GameDto> getGames() {
        return service.getGames();
    }

    @PostMapping("/add")
    public ResponseEntity addGame(@RequestBody GameDto game)
    {
        try{
            return new ResponseEntity(service.save(game), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }

    @PostMapping("/update")
    public ResponseEntity updateGame(@RequestBody GameDto game)
    {
        try{
            return new ResponseEntity(service.update(game), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }

    @PostMapping("/delete")
    public ResponseEntity deleteGame(@RequestBody GameDto game)
    {
        try{
            service.delete(game);
            return new ResponseEntity( HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }

}
