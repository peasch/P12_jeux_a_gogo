package com.peasch.jeuxagogo.repository;

import com.peasch.jeuxagogo.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Integer> {

    Role findByRole(String role);
}
