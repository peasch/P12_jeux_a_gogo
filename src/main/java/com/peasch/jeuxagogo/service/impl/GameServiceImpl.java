package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.GameMapper;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.entities.Game;
import com.peasch.jeuxagogo.repository.GameDao;
import com.peasch.jeuxagogo.service.*;
import com.peasch.jeuxagogo.service.misc.Text_FR;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AdviceService adviceService;
    @Autowired
    private BorrowingService borrowingService;
    @Autowired
    private WaitListService waitListService;


    //--------------------------------Metier--------------------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<GameDto> getGames() {
        List<GameDto> games = dao.findAll().stream().map(mapper::fromGameToStrictDto)
                .collect(Collectors.toList());
        for (GameDto gameDto : games) {
            this.setAverageRating(gameDto);

        }
        List<GameDto> games3 = dao.findAll().stream().map(mapper::fromGameToStrictDto)
                .collect(Collectors.toList());
        for (GameDto gameDto : games3) {
            int id = gameDto.getId();
            if (copyService.getAvailableCopiesByGameId(id).isEmpty()) {
                gameDto.setAvailable(false);
                this.update(gameDto);
            } else {
                if (waitListService.getWaitlistByGameId(gameDto.getId()).isEmpty()) {
                    gameDto.setAvailable(true);
                } else {
                    gameDto.setAvailable(false);
                }
                this.update(gameDto);
            }
        }
        List<GameDto> games2 = dao.findAll().stream().map(mapper::fromGameToStrictDto)
                .collect(Collectors.toList());

        Collections.sort(games2, new Comparator<GameDto>() {
            @Override
            public int compare(final GameDto o1, final GameDto o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return games2;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<GameDto> getGamesByRating() {
        return dao.findAll().stream().map(mapper::fromGameToStrictDto)
                .sorted(Comparator.comparing(GameDto::getRating))
                .collect(Collectors.toList());

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<Integer> getGamesByAgeMin() {
        List<Integer> games = dao.findDistinctByAgeMin();
        Collections.sort(games);
        return games;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<Integer> getGamesByMinPlayers() {
        List<Integer> games = dao.findDistinctByMinPlayers();
        Collections.sort(games);
        return games;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<GameDto> getGamesByName(String name) {
        return dao.findGamesByNameLike("%" + name + "%").stream().map(mapper::fromGameToStrictDto).sorted(Comparator.comparing(GameDto::getName))
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<GameDto> getGamesByStyleId(int id) {
        return dao.findGamesByGameStyle_Id(id).stream().map(mapper::fromGameToStrictDto)
                .sorted(Comparator.comparing(GameDto::getName)).collect(Collectors.toList());

    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<GameDto> getGamesByAgeMinResearched(int ageMin) {
        return dao.findGamesByAgeMinEquals(ageMin).stream().map(mapper::fromGameToStrictDto)
                .sorted(Comparator.comparing(GameDto::getName))
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<GameDto> getGamesByPopularity() {
        return dao.findAll().stream().map(mapper::fromGameToStrictDto)
                .sorted(Comparator.comparing(GameDto::getBorrowingQuantity)).collect(Collectors.toList());
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public GameDto save(GameDto gameDto) {
        this.validationOfNewGame(gameDto);
        gameDto.setAvailable(false);
        gameDto.setBorrowingQuantity(0);
        gameDto.setRating(-1.0f);
        return mapper.fromGameToStrictDto(dao.save(mapper.fromDtoToGame(gameDto)));

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
        game.setDescription(gameToUpdateDto.getDescription());
        game.setCoverLink(gameToUpdateDto.getCoverLink());
        game.setRating(gameToUpdateDto.getRating());
        game.setBorrowingQuantity(gameToUpdateDto.getBorrowingQuantity());
        /*CustomConstraintValidation<GameDto> customConstraintValidation = new CustomConstraintValidation<>();
        customConstraintValidation.validate(game);*/

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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public GameDto setAvailable(GameDto gameDto) {
        GameDto game = this.findById(gameDto.getId());
        game.setAvailable(waitListService.getWaitlistByGameId(game.getId()).isEmpty());
        return mapper.fromGameToStrictDto(dao.save(mapper.fromDtoToGame(game)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public GameDto setAverageRating(GameDto gameDto) {
        GameDto gameToRate = this.findById(gameDto.getId());
        gameToRate.setBorrowingQuantity(borrowingService.getAllBorrowingsByGameId(gameDto.getId()).size());
        if (!adviceService.getAllAdviceById(gameDto.getId()).isEmpty()) {
            gameToRate.setRating(adviceService.getRatingOfGameId(gameToRate.getId()));
            return mapper.fromGameToStrictDto(dao.save(mapper.fromDtoToGame(gameToRate)));
        }
        return gameDto;
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
