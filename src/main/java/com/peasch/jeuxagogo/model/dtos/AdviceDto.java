package com.peasch.jeuxagogo.model.dtos;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    @Min(value = 1)
    @Max(value = 5)
    private int rating;
    private String username;
}
