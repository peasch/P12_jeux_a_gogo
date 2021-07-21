package com.peasch.jeuxagogo.model.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GameStyleDto implements Serializable {

    private int id;
    @NotNull
    private String name;
    private Set<GameDto> games;
}
