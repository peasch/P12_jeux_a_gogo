package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.GameDto;

import javax.validation.ValidationException;
import java.util.List;

public interface GameService {

    List<GameDto> getGames();
    GameDto save (GameDto gameDto) throws ValidationException;
    GameDto update(GameDto gameToUpdateDto)throws ValidationException;
    void delete(int id);
}
