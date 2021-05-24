package com.peasch.jeuxagogo.model.dtos;

import com.peasch.jeuxagogo.model.entities.Copy;
import com.peasch.jeuxagogo.model.entities.Editor;
import com.peasch.jeuxagogo.model.entities.GameStyle;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GameDto implements Serializable {

    private int id;
    @NotNull
    private String name;
    @NotNull
    private int ageMin;

    private int minPlayers;
    private int maxPlayers;
    private Boolean available;

    private String rulesLink;
    @NotNull
    private EditorDto editorDto;
    @NotNull
    private GameStyleDto gameStyleDto;
    @NotNull
    private Set<CopiesDto> copiesDto;

}
