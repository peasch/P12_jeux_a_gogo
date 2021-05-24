package com.peasch.jeuxagogo.controller;

import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
