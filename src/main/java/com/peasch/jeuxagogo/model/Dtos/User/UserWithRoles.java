package com.peasch.jeuxagogo.model.Dtos.User;

import com.peasch.jeuxagogo.model.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor @Getter @Setter @NoArgsConstructor
public class UserWithRoles extends UserDto{

    private Set<Role> roles;
}
