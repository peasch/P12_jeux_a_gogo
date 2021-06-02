package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.GameStyleMapper;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.dtos.GameStyleDto;
import com.peasch.jeuxagogo.model.entities.GameStyle;
import com.peasch.jeuxagogo.repository.GameStyleDao;
import com.peasch.jeuxagogo.service.GameStyleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class GameStyleServiceImpl implements GameStyleService {

    @Autowired
    private GameStyleMapper mapper;

    @Autowired
    private GameStyleDao dao;


    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    //------------------------------Métiers-------------------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<GameStyleDto> getGameStyles(){
        return dao.findAll().stream().map(mapper::fromStyletoDtoWithoutGames)
                .collect(Collectors.toList());
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

}
