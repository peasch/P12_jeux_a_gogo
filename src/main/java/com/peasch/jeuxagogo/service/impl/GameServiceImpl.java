package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.GameMapper;
import com.peasch.jeuxagogo.model.dtos.CopyDto;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.repository.GameDao;
import com.peasch.jeuxagogo.service.CopyService;
import com.peasch.jeuxagogo.service.GameService;
import com.peasch.jeuxagogo.service.misc.Text_FR;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class GameServiceImpl implements GameService {

    @Autowired
    private GameMapper mapper;

    @Autowired
    private GameDao dao;

    @Autowired
    private CopyService copyService;


    //--------------------------------Metier--------------------------------------------


    public List<GameDto> getGames() {
        return dao.findAll().stream().map(mapper::fromGameToStrictDto)
                .collect(Collectors.toList());
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public GameDto save(GameDto gameDto) {
        this.validationOfNewGame(gameDto);
        GameDto savedGame = mapper.fromGameToStrictDto(dao.save(mapper.fromDtoToGame(gameDto)));
        if (savedGame.getCopiesDto() != null) {
            for (CopyDto copy : savedGame.getCopiesDto()) {
                copyService.save(copy);
            }
        }

        return savedGame;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public GameDto update(GameDto gameToUpdateDto) {
        GameDto game = this.findById(gameToUpdateDto.getId());
        game.setMaxPlayers(gameToUpdateDto.getMaxPlayers());
        game.setEditorDto(gameToUpdateDto.getEditorDto());
        game.setCopiesDto(gameToUpdateDto.getCopiesDto());
        game.setGameStyleDto(gameToUpdateDto.getGameStyleDto());
        game.setName(gameToUpdateDto.getName());
        game.setAvailable(gameToUpdateDto.getAvailable());
        game.setAgeMin(gameToUpdateDto.getAgeMin());
        game.setMinPlayers(gameToUpdateDto.getMinPlayers());
        game.setRulesLink(gameToUpdateDto.getRulesLink());

        CustomConstraintValidation<GameDto> customConstraintValidation = new CustomConstraintValidation<>();
        customConstraintValidation.validate(gameToUpdateDto);

        return mapper.fromGameToStrictDto(dao.save(mapper.fromDtoToGame(game)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(int id) {
        copyService.deleteAllByGameId(id);
        dao.delete(mapper.fromDtoToGame(this.findById(id)));
    }

    //--------------------------Game findings -----------------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public GameDto findByName(String name) {
        return mapper.fromGameToStrictDto(dao.findGameByName(name));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public GameDto findById(int id) {
        return mapper.fromGameToStrictDto(dao.findById(id).get());
    }

    //--------------------checking fields----------------------------------------------

    private boolean checkName(String name) {
        return this.findByName(name) != null;
    }

    //--------------------------VALIDATIONS---------------------------------


    private void validationOfNewGame(GameDto gameDto) throws ValidationException {
        if (this.checkName(gameDto.getName())) {
            Log.info(Text_FR.ALREADY_USED_GAME_NAME);
            throw new ValidationException(Text_FR.ALREADY_USED_GAME_NAME);
        }
        CustomConstraintValidation<GameDto> customConstraintValidation = new CustomConstraintValidation<>();
        customConstraintValidation.validate(gameDto);

    }


}
