package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.EditorMapper;
import com.peasch.jeuxagogo.model.dtos.EditorDto;
import com.peasch.jeuxagogo.model.entities.Editor;
import com.peasch.jeuxagogo.repository.EditorDao;
import com.peasch.jeuxagogo.service.EditorService;
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
@Slf4j
public class EditorServiceImpl implements EditorService {

    @Autowired
    private EditorMapper mapper;

    @Autowired
    private EditorDao dao;


    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    //----------------------------------Métier-------------------------------------------


    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<EditorDto> getEditors(){
        return dao.findAll().stream().map(mapper::fromEditorToDtoWithoutGames).collect(Collectors.toList());
    }


    //---------------------------------Finding Editors-----------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public EditorDto findById(int id){
        return mapper.fromEditorToDtoWithoutGames(dao.findById(id).get());
    }
}
