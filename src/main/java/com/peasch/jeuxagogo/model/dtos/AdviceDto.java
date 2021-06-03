package com.peasch.jeuxagogo.model.dtos;

import com.peasch.jeuxagogo.model.entities.Game;
import com.peasch.jeuxagogo.model.entities.User;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AdviceDto {
    private int id;
    @NotNull
    private GameDto game;
    @NotNull
    private UserDto user;
    private String commentary;
    @NotNull
    private int rating;
}
