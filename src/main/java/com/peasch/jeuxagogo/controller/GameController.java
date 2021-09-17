package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.dtos.GameDto;
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

    @GetMapping("/all/rating")
    public List<GameDto> getGamesByRating() {
        return service.getGamesByRating();
    }

    @GetMapping("/all/popularity")
    public List<GameDto> getGamesByPopularity() {
        return service.getGamesByPopularity();
    }

    @GetMapping("/all/age")
    public List<Integer> getGamesByAge() {
        return service.getGamesByAgeMin();
    }

    @GetMapping("/all/age/{ageMin}")
    public List<GameDto> getGamesByAgeMinResearched(@PathVariable(name = "ageMin") int ageMin) {
        return service.getGamesByAgeMinResearched(ageMin);
    }

    @GetMapping("/all/minPlayers")
    public List<Integer> getGamesByMinPlayers() {
        return service.getGamesByMinPlayers();
    }

    @GetMapping("/all/style/{id}")
    public List<GameDto> getGamesBystyleId(@PathVariable(name = "id") int id) {
        return service.getGamesByStyleId(id);
    }

    @GetMapping("/all/name/{name}")
    public List<GameDto> getGamesByName(@PathVariable(name = "name") String name) {
        return service.getGamesByName(name);
    }

    @GetMapping("/id/{id}")
    public GameDto getGameById(@PathVariable(name = "id") int id) {
        return service.findById(id);

    }

    @PostMapping("/add")
    public ResponseEntity addGame(@RequestBody GameDto game) {
        try {
            return new ResponseEntity(service.save(game), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }

    @PutMapping("/update")
    public ResponseEntity updateGame(@RequestBody GameDto game) {
        try {
            return new ResponseEntity(service.update(game), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteGame(@PathVariable(name = "id") int id) {
        try {
            service.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }


}
