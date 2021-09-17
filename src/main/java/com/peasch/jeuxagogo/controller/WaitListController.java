package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.dtos.WaitListDto;
import com.peasch.jeuxagogo.service.WaitListService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/waitlist")
public class WaitListController {
    @Autowired
    private WaitListService service;

    @GetMapping("/all")
    public List<WaitListDto> getAllWaitLists() {
        return service.getWaitlists();
    }

    @GetMapping("/user/{id}")
    public List<WaitListDto> getAllWaitListOfUserId(@PathVariable(name = "id") int id) {
        return service.getWaitlistByWaiterId(id);
    }

    @GetMapping("/game/{id}")
    public List<WaitListDto> getAllWaitListOfGameId(@PathVariable(name = "id") int id) {
        return service.getWaitlistByGameId(id);
    }

    @PostMapping("/game/user/{id}")
    public boolean isUserOnWaitListOfGameId(@PathVariable(name = "id") int id, @RequestBody String username) {
        return service.getWaitlistByWaiterIdAndGameId(id, username) != null;
    }

    @PostMapping("/add/{id}")
    public ResponseEntity addWaitListToGame(@PathVariable(name = "id") int id, @RequestBody String username) {
        try {
            return new ResponseEntity(service.addWaitListToGame(id, username), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteWaitList(@PathVariable(name = "id") int id) {
        try {
            service.deleteWaitList(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("skip/{id}")
    public ResponseEntity skipWaitLister(@PathVariable(name = "id") int id) {
        try {
            service.deleteWaitListAndContactNext(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }
}
