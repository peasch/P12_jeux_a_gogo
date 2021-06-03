package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.RoleMapper;
import com.peasch.jeuxagogo.repository.RoleDao;
import com.peasch.jeuxagogo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper mapper;

    @Autowired
    private RoleDao dao;

    //-----------------------------Métiers--------------------------
}
