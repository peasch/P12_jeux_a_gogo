package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> getRoles();
    RoleDto findById(int id);
    RoleDto findByRole(String role);
}
