package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.GameMapper;
import com.peasch.jeuxagogo.model.dtos.CopyDto;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.entities.Game;
import com.peasch.jeuxagogo.repository.GameDao;
import com.peasch.jeuxagogo.service.CopyService;
import com.peasch.jeuxagogo.service.GameService;
import com.peasch.jeuxagogo.service.GameStyleService;
import com.peasch.jeuxagogo.service.misc.Text_FR;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.Collections;
import java.util.Comparator;
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
    @Autowired
    private GameStyleService gameStyleService;


    //--------------------------------Metier--------------------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<GameDto> getGames() {
        List<GameDto> games = dao.findAll().stream().map(mapper::fromGameToStrictDto)
                .collect(Collectors.toList());

        for (GameDto gameDto : games) {
            int id= gameDto.getId();
            if (copyService.getAvailableCopiesByGameId(id).isEmpty()) {
                gameDto.setAvailable(false);
                this.update(gameDto);
            }else{
                gameDto.setAvailable(true);
                this.update(gameDto);
            }
        }
          List<GameDto> games2 =dao.findAll().stream().map(mapper::fromGameToStrictDto)
                .collect(Collectors.toList());

        Collections.sort(games2, new Comparator<GameDto>() {
            @Override
            public int compare(final GameDto o1,final GameDto o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
return games2;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public GameDto save(GameDto gameDto) {

        this.validationOfNewGame(gameDto);
        gameDto.setAvailable(false);

        GameDto savedGame = mapper.fromGameToStrictDto(dao.save(mapper.fromDtoToGame(gameDto)));
        if (savedGame.getCopiesDto() != null) {
            for (CopyDto copy : savedGame.getCopiesDto()) {
                copyService.save(copy, gameDto.getId());
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
        customConstraintValidation.validate(game);

        return mapper.fromGameToStrictDto(dao.save(mapper.fromDtoToGame(game)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(int id) {
        copyService.deleteAllByGameId(id);
        dao.delete(mapper.fromDtoToGame(this.findById(id)));
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public GameDto setUnavailable(GameDto gameDto) {
        GameDto borrowedGame = this.findById(gameDto.getId());
        borrowedGame.setAvailable(false);
        return mapper.fromGameToStrictDto(dao.save(mapper.fromDtoToGame(borrowedGame)));
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

    private void checkAvailabilityOfGame() {
        List<CopyDto> copies = copyService.getAll();
        for (CopyDto copyDto : copies) {
            copyDto.getGame().setAvailable(copyDto.getAvailable());
        }
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
