package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.dtos.BorrowingDto;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/borrowing")
public class BorrowingController {

    @Autowired
    private BorrowingService service;

    @PostMapping("/add")
    public ResponseEntity addBorrowing(@RequestBody BorrowingDto borrowingDto)
    {
        try{
            return new ResponseEntity(service.save(borrowingDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }

}
