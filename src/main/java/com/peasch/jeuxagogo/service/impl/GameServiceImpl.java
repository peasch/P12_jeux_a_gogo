package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.GameMapper;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.repository.GameDao;
import com.peasch.jeuxagogo.service.GameService;
import com.peasch.jeuxagogo.service.misc.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class GameServiceImpl implements GameService {

    @Autowired
    private GameMapper mapper;

    @Autowired
    private GameDao dao;


    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    //--------------------------------Metier--------------------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<GameDto> getGames() {
        return dao.findAll().stream().map(mapper::fromGameToStrictDto)
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public GameDto save(GameDto gameDto) {
        this.validationOfNewGame(gameDto);
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
        this.validationOfUpdatingGame(game);
        return mapper.fromGameToStrictDto(dao.save(mapper.fromDtoToGame(game)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(int id) {
        dao.delete(mapper.fromDtoToGame(this.findById(id)));
    }

    //--------------------------Game findings -----------------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public GameDto findByName(String name) {
        return mapper.fromGameToStrictDto(dao.findGameByName(name));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public GameDto findById(int id){
        return mapper.fromGameToStrictDto(dao.findById(id).get());
    }

    //--------------------checking fields----------------------------------------------

    private boolean checkName(String name) {
        return this.findByName(name) != null;
    }

    //--------------------------VALIDATIONS---------------------------------
    private void constraintValidation(Set<ConstraintViolation<GameDto>> constraintViolations) {
        if (!constraintViolations.isEmpty()) {
            System.out.println(Text.INVALID_GAME);

            for (ConstraintViolation<GameDto> contraintes : constraintViolations) {
                System.out.println(contraintes.getRootBeanClass().getSimpleName() +
                        "." + contraintes.getPropertyPath() + " " + contraintes.getMessage());
            }
            throw new ValidationException(Text.INCORRECT_INFORMATION);
        }
    }

    private void validationOfNewGame(GameDto gameDto) throws ValidationException {
        Set<ConstraintViolation<GameDto>> constraintViolations = validator.validate(gameDto);

        if (this.checkName(gameDto.getName())) {
            throw new ValidationException(Text.ALREADY_USED_GAME_NAME);
        }
        this.constraintValidation(constraintViolations);
    }

    private void validationOfUpdatingGame(GameDto gameToUpdateDto) throws ValidationException {
        Set<ConstraintViolation<GameDto>> constraintViolations = validator.validate(gameToUpdateDto);
        this.constraintValidation(constraintViolations);
    }
}
