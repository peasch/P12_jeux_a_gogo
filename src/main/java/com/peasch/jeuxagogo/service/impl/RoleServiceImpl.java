package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.RoleMapper;
import com.peasch.jeuxagogo.model.dtos.EditorDto;
import com.peasch.jeuxagogo.model.dtos.RoleDto;
import com.peasch.jeuxagogo.repository.RoleDao;
import com.peasch.jeuxagogo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper mapper;

    @Autowired
    private RoleDao dao;

    //-----------------------------Métiers--------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<RoleDto> getRoles() {
        return dao.findAll().stream().map(mapper::fromRoletoDto).collect(Collectors.toList());
    }



    //------------------------------FInding -----------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public RoleDto findById(int id){
        return mapper.fromRoletoDto(dao.findById(id).get());
    }




}
