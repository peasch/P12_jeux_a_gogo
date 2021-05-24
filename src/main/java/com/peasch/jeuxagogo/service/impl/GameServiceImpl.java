package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.GameMapper;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.model.entities.Game;
import com.peasch.jeuxagogo.repository.GameDao;
import com.peasch.jeuxagogo.service.GameService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log
public class GameServiceImpl implements GameService {

    @Autowired
    private GameMapper mapper;

    @Autowired
    private GameDao dao;


    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    //--------------------------------Metier--------------------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<GameDto> getGames() {
        List<Game> games = dao.findAll();
        return games.stream().map(x -> mapper.fromGameToStrictDto(x)).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public GameDto save (GameDto gameDto) throws Exception {
        this.validationOfGame(gameDto);
        return mapper.fromGameToStrictDto(dao.save(mapper.fromDtoToGame(gameDto)));
    }

    //--------------------------Game findings -----------------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public GameDto findByName(String name) {
        return mapper.fromGameToStrictDto(dao.findGameByName(name));
    }
    //--------------------checking fields----------------------------------------------

    private boolean checkName(String name) {
        return this.findByName(name) != null;
    }

    private void validationOfGame(GameDto gameDto) throws Exception {
        Set<ConstraintViolation<GameDto>> constraintViolations = validator.validate(gameDto);

        if(this.checkName(gameDto.getName())){
            throw new  Exception("Ce nom de jeu existe déjà dans la liste");

        }
        if (!constraintViolations.isEmpty()) {
            System.out.println("Impossible de valider les informations du jeu : ");
            for (ConstraintViolation<GameDto> contraintes : constraintViolations) {
                System.out.println(contraintes.getRootBeanClass().getSimpleName() +
                        "." + contraintes.getPropertyPath() + " " + contraintes.getMessage());
            }
            throw new Exception("les informations sont incorrectes");
        }

    }

}
