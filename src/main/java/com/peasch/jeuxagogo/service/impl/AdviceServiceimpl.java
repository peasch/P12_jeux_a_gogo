package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.AdviceMapper;
import com.peasch.jeuxagogo.model.dtos.AdviceDto;
import com.peasch.jeuxagogo.repository.AdviceDao;
import com.peasch.jeuxagogo.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdviceServiceimpl implements AdviceService {

    @Autowired
    private AdviceMapper mapper;
    @Autowired
    private AdviceDao dao;


    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<AdviceDto> getAdvices(){
        return dao.findAll().stream().map(mapper::fromAdviceToDto).collect(Collectors.toList());
    }


}
