package com.peasch.jeuxagogo.model.dtos;

import com.peasch.jeuxagogo.model.entities.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoleDto {

    private int id;
    private String role;
    private Set<UserDto> users;
}

