package com.peasch.jeuxagogo.model.Mappers;

import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.entities.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {EditorMapper.class, GameStyleMapper.class})

public interface GameMapper {


    @Mapping(target = "editor", source = "editorDto")
    @Mapping(target = "gameStyle", source = "gameStyleDto")
    @Mapping(target = "copies", source = "copiesDto")
    Game fromDtoToGame(GameDto gameDto);


    @Named("toStrictDto")
    @Mapping(target = "gameStyleDto", source = "game.gameStyle", qualifiedByName = "withoutGames")
    @Mapping(target = "copiesDto", ignore = true)
    @Mapping(target = "editorDto", source = "editor", qualifiedByName = "withoutGames")
    GameDto fromGameToStrictDto(Game game);

}
