package com.peasch.jeuxagogo.model.Mappers;

import com.peasch.jeuxagogo.model.dtos.GameStyleDto;
import com.peasch.jeuxagogo.model.entities.Game;
import com.peasch.jeuxagogo.model.entities.GameStyle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GameStyleMapper {

    GameStyle fromDtoToStyle(GameStyleDto gameStyleDto);
    @Named("withoutGames")
    @Mapping(target = "games", ignore = true)
    GameStyleDto fromStyletoDtoWithoutGames(GameStyle gameStyle);
}
