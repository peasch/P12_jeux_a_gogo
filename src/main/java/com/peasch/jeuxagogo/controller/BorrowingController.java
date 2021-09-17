package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.dtos.BorrowingDto;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OrderBy;
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
    @GetMapping("/returned/{username}")
    public List<BorrowingDto> getReturnedBorrowingsOfUser(@PathVariable(name = "username") String username) {
        return service.getReturnedBorrowingsByUsername(username);
    }
    @GetMapping("/unreturned/{username}")
    public List<BorrowingDto> getUnreturnedBorrowingsOfUser(@PathVariable(name = "username") String username) {
        return service.getUnreturnedBorrowingsByUsername(username);
    }

    @GetMapping("/pending/{username}")
    public List<BorrowingDto> getPendingBorrowingsOfUser(@PathVariable(name = "username") String username) {
        return service.getPendingBorrowingsByUsername(username);
    }
    @GetMapping("/unreturned")
    public List<BorrowingDto> getUnreturnedBorrowings() {
        return service.getUnreturnedBorrowings();
    }
    @GetMapping("/returned")
    public List<BorrowingDto> getReturnedBorrowings() {
        return service.getReturnedBorrowings();
    }
    @GetMapping("/allPending")
    public List<BorrowingDto> getPendingBorrowings() {
        return service.getAllPendingBorrowings();
    }



    @PostMapping("/add/{id}")
    public ResponseEntity addBorrowing(@PathVariable(name = "id")Integer id,@RequestBody String username) {

        try{
            return new ResponseEntity(service.add(username,id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }
    @PostMapping("/addFromWL/{id}")
    public ResponseEntity addBorrowingFromWaitList(@PathVariable(name = "id")Integer id,@RequestBody String username) {

        try{
            return new ResponseEntity(service.addfromWaitList(username,id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }
    @GetMapping("/return/{id}")
    public ResponseEntity returnBorrowing(@PathVariable(name = "id")int id){
        try{
            return new ResponseEntity(service.returnborrowing(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBorrowing(@PathVariable(name = "id") int id){

        try{
            service.delete(id);
            return new ResponseEntity( HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }

    @GetMapping("/valid/{id}")
    public ResponseEntity validBorrowing(@PathVariable(name = "id") int id){
        try{
            service.validborrowing(id);
            return new ResponseEntity( HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        }
    }

}
