package com.peasch.jeuxagogo.model.Mappers;

import com.peasch.jeuxagogo.model.dtos.RoleDto;
import com.peasch.jeuxagogo.model.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class})
public interface RoleMapper {

    Role fromDtoToRole(RoleDto roleDto);

    @Named("toStrictDto")
    @Mapping(target = "users", ignore = true)
    RoleDto fromRoletoDto(Role role);

    @Named("toDtoWithUsersWithoutRoles")
    @Mapping(target = "users", source = "users",qualifiedByName = "toDtoWithoutRoles")
    RoleDto fromRoleToDtoWithUsers(Role role);
}
