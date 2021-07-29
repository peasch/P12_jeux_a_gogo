package com.peasch.jeuxagogo.model.dtos;

import lombok.*;

import javax.persistence.OrderBy;
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
    @OrderBy
    private String name;

    private Integer ageMin;

    private int minPlayers;
    private int maxPlayers;
    private Boolean available;
    private Integer duration;
    private String rulesLink;
    private Boolean french;
    private EditorDto editorDto;
    private GameStyleDto gameStyleDto;
    private Set<CopyDto> copiesDto;

}
