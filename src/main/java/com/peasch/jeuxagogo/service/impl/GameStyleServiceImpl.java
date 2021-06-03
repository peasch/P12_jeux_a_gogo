package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.GameStyleMapper;
import com.peasch.jeuxagogo.model.dtos.CopyDto;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.dtos.GameStyleDto;
import com.peasch.jeuxagogo.model.entities.GameStyle;
import com.peasch.jeuxagogo.repository.GameStyleDao;
import com.peasch.jeuxagogo.service.GameStyleService;
import com.peasch.jeuxagogo.service.misc.Text_FR;
import lombok.extern.slf4j.Slf4j;
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

public class GameStyleServiceImpl implements GameStyleService {

    @Autowired
    private GameStyleMapper mapper;

    @Autowired
    private GameStyleDao dao;


    //------------------------------Métiers-------------------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<GameStyleDto> getGameStyles(){
        return dao.findAll().stream().map(mapper::fromStyletoDtoWithoutGames)
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public GameStyleDto save(GameStyleDto gameStyleDto) {
        this.validationOfNewGameStyle(gameStyleDto);
        return mapper.fromStyletoDtoWithoutGames(dao.save(mapper.fromDtoToStyle(gameStyleDto)));

    }


    //---------------------------------Findings----------------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public GameStyleDto findById(int id) {
        return mapper.fromStyletoDtoWithoutGames(dao.findById(id).get());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public GameStyleDto findByName(String name) {
        return mapper.fromStyletoDtoWithoutGames(dao.findByName(name));
    }


    //--------------------------------Checking fields----------------------------------

    private boolean checkName(String name){
        return this.findByName(name)!= null;
    }

    //---------------------------- Validation -----------------------------------------

    private void validationOfNewGameStyle(GameStyleDto gameStyleDto) throws ValidationException {
        if (this.checkName(gameStyleDto.getName())) {
            Log.info(Text_FR.ALREADY_USED_GAME_NAME);
            throw new ValidationException(Text_FR.ALREADY_USED_GAME_NAME);
        }
        CustomConstraintValidation<GameStyleDto> customConstraintValidation = new CustomConstraintValidation<>();
        customConstraintValidation.validate(gameStyleDto);

    }
}
