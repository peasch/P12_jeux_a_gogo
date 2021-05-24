package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.GameDto;

import java.util.List;

public interface GameService {

    List<GameDto> getGames();
}
