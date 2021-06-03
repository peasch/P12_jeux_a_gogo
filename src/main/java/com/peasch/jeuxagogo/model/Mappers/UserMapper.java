package com.peasch.jeuxagogo.model.Mappers;

import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.model.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
uses = {RoleMapper.class})
public interface UserMapper {

    User fromDtoToUser(UserDto userDto);

    @Named("toStrictDto")
    @Mapping(target = "rolesDto", ignore = true)
    @Mapping(target = "godfather", ignore = true)
    @Mapping(target = "godsons", ignore = true)
    @Mapping(target = "askedFriends", ignore = true)
    @Mapping(target = "askerUsers", ignore = true)
    @Mapping(target = "advices", ignore = true)
    UserDto fromUserToStrictDto(User user);

    @Named("toDtoWithRoles")
    @Mapping(target = "rolesDto", source = "roles",qualifiedByName = "toStrictDto")
    @Mapping(target = "godfather", ignore = true)
    @Mapping(target = "godsons", ignore = true)
    @Mapping(target = "askedFriends", ignore = true)
    @Mapping(target = "askerUsers", ignore = true)
    @Mapping(target = "advices", ignore = true)
    UserDto fromUserToDtoWithrole(User user);

}
