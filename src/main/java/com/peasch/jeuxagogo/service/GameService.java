package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.GameDto;

import javax.validation.ValidationException;
import java.util.List;

public interface GameService {

    List<GameDto> getGames();

    GameDto findById(int id);
    GameDto save (GameDto gameDto) throws ValidationException;
    GameDto update(GameDto gameToUpdateDto)throws ValidationException;
    GameDto setUnavailable(GameDto gameDto);
    void delete(int id);
}
