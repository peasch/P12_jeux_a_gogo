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
public class CopyDto implements Serializable {
    private int id;
    @NotNull
    private String code;

    private Boolean available;
    @NotNull
    private GameDto game;
}
