package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.dtos.BorrowingDto;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowing")
public class BorrowingController {

    @Autowired
    private BorrowingService service;

    @GetMapping("/all")
    public List<BorrowingDto> getGames() {
        return service.getBorrowings();
    }

    @GetMapping("/{username}")
    public List<BorrowingDto> getBorrowingsOfUser(@PathVariable(name = "username") String username) {
        return service.getBorrowingsByUsername(username);
    }

    @PostMapping("/add/{id}")
    public ResponseEntity addBorrowing(@PathVariable(name = "id")Integer id,@RequestBody String username) {

        try{
            return new ResponseEntity(service.add(username,id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }

}
