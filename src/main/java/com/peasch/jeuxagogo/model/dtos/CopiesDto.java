package com.peasch.jeuxagogo.model.dtos;

import com.peasch.jeuxagogo.model.entities.Game;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CopiesDto implements Serializable {
    private int id;
    @NotNull
    private String code;
    @NotNull
    private Boolean available;
    @NotNull
    private GameDto game;
}
