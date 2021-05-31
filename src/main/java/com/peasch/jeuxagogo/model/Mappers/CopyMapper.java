package com.peasch.jeuxagogo.model.Mappers;

import com.peasch.jeuxagogo.model.dtos.CopiesDto;
import com.peasch.jeuxagogo.model.entities.Copy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CopyMapper {

    Copy fromDtoToCopy (CopiesDto copiesDto);

    @Named("withoutGames")
    @Mapping(target = "game",ignore = true)
    CopiesDto fromCopyToDtoWithoutGame(Copy copy);
}
