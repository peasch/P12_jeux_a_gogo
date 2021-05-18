package com.peasch.jeuxagogo.model.Mappers;

import com.peasch.jeuxagogo.model.Dtos.User.UserDto;
import com.peasch.jeuxagogo.model.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User fromDtoToUser(UserDto userDto);
    UserDto fromUserToDto(User user);
}
