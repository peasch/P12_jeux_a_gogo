package com.peasch.jeuxagogo.controller;
import com.peasch.jeuxagogo.model.dtos.GameStyleDto;
import com.peasch.jeuxagogo.service.GameStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
