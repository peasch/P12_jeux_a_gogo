package com.peasch.jeuxagogo.model.Mappers;

import com.peasch.jeuxagogo.model.dtos.CopyDto;
import com.peasch.jeuxagogo.model.entities.Copy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
uses = {UserMapper.class,GameMapper.class})
public interface CopyMapper {

    Copy fromDtoToCopy (CopyDto copyDto);

    @Named("withoutGames")
    @Mapping(target = "game",ignore = true)
    CopyDto fromCopyToDtoWithoutGame(Copy copy);

    @Named("withGames")
    @Mapping(target = "game",source = "game", qualifiedByName = "withoutDetails")
    CopyDto fromCopyToDtoWithGame(Copy copy);
}
