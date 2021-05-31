package com.peasch.jeuxagogo.model.Mappers;

import com.peasch.jeuxagogo.model.dtos.CopyDto;
import com.peasch.jeuxagogo.model.entities.Copy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CopyMapper {

    Copy fromDtoToCopy (CopyDto copyDto);

    @Named("withoutGames")
    @Mapping(target = "game",ignore = true)
    CopyDto fromCopyToDtoWithoutGame(Copy copy);
}
