package com.peasch.jeuxagogo.model.dtos;

import com.peasch.jeuxagogo.model.entities.Game;
import com.peasch.jeuxagogo.model.entities.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WaitListDto {
    private int id;
    @NotNull
    private UserDto waiter;
    @NotNull
    private LocalDate returnDate;
    @NotNull @Past
    private LocalDate date;

    private LocalDate contactDate;
    @NotNull
    private GameDto game;
}
