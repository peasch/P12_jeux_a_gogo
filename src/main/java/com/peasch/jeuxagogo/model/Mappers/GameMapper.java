package com.peasch.jeuxagogo.model.Mappers;

import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.entities.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {EditorMapper.class, GameStyleMapper.class, CopyMapper.class})

public interface GameMapper {


    @Mapping(target = "editor", source = "editorDto")
    @Mapping(target = "gameStyle", source = "gameStyleDto")
    @Mapping(target = "copies", source = "copiesDto")
    @Mapping(target = "coverLink",source = "coverLink")
    Game fromDtoToGame(GameDto gameDto);


    @Named("toStrictDto")
    @Mapping(target = "gameStyleDto", source = "gameStyle", qualifiedByName = "withoutGames")
    @Mapping(target = "copiesDto", source = "copies", qualifiedByName = "withoutGames")
    @Mapping(target = "editorDto", source = "editor", qualifiedByName = "withoutGames")
    @Mapping(target = "coverLink",source = "coverLink")
    GameDto fromGameToStrictDto(Game game);

    @Named("withoutDetails")
    @Mapping(target = "gameStyleDto", ignore = true)
    @Mapping(target = "copiesDto", ignore = true)
    @Mapping(target = "editorDto", ignore = true)
    GameDto fromGameTotDtoWithoutDetails(Game game);

}
