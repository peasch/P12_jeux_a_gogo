package com.peasch.jeuxagogo.model.dtos;

import com.peasch.jeuxagogo.model.entities.Copy;
import com.peasch.jeuxagogo.model.entities.Editor;
import com.peasch.jeuxagogo.model.entities.GameStyle;
import lombok.*;

import javax.validation.constraints.Min;
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
    private Integer ageMin;

    private int minPlayers;
    private int maxPlayers;
    private Boolean available;

    private String rulesLink;

    private EditorDto editorDto;

    private GameStyleDto gameStyleDto;

    private Set<CopiesDto> copiesDto;

}
