package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.GameStyleDto;

import java.util.List;

public interface GameStyleService {
    List<GameStyleDto> getGameStyles();
    GameStyleDto findById(int id);
    GameStyleDto save(GameStyleDto gameStyleDto);
    void delete(int id);
}
