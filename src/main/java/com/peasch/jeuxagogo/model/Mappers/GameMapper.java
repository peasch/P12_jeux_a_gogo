package com.peasch.jeuxagogo.model.Mappers;

import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {EditorMapper.class, GameStyleMapper.class})

public interface GameMapper {

    Game fromDtoToGame(GameDto gameDto);

    @Named("toStrictDto")
    @Mapping(target = "gameStyleDto", source = "gameStyle",qualifiedByName = "withoutGames")
    @Mapping(target = "copiesDto", ignore = true)
    @Mapping(target = "editorDto", source = "editor", qualifiedByName = "withoutGames")
    GameDto fromGameToStrictDto(Game game);


}
