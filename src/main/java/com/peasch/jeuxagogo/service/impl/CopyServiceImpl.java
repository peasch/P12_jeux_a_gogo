package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.CopyMapper;
import com.peasch.jeuxagogo.model.dtos.CopiesDto;
import com.peasch.jeuxagogo.repository.CopyDao;
import com.peasch.jeuxagogo.service.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CopyServiceImpl implements CopyService {

    @Autowired
    private CopyMapper mapper;

    @Autowired
    private CopyDao dao;


    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    //------------------------Métier----------------------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<CopiesDto> getAll (){
        return dao.findAll().stream().map(mapper::fromCopyToDtoWithoutGame).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<CopiesDto> getCopiesByGameId(int id){
        return dao.findAllByGame_Id(id).stream().map(mapper::fromCopyToDtoWithoutGame).collect(Collectors.toList());
    }
}
