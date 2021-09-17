package com.peasch.jeuxagogo.model.dtos;

import lombok.*;

import java.io.Serializable;

import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoleDto implements Serializable {

    private int id;
    private String role;
    private Set<UserDto> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDto roleDto = (RoleDto) o;
        return id == roleDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

