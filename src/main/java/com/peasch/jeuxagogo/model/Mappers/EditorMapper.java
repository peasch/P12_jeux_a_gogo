package com.peasch.jeuxagogo.model.Mappers;

import com.peasch.jeuxagogo.model.dtos.EditorDto;
import com.peasch.jeuxagogo.model.entities.Editor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EditorMapper {

    Editor fromDtoToEditor (EditorDto editorDto);

    @Named("withoutGames")
    @Mapping(target = "games", ignore = true)
    EditorDto fromEditorToDtoWithoutGames(Editor editor);
}
