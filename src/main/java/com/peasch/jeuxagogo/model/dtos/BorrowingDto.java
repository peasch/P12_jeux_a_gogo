package com.peasch.jeuxagogo.model.dtos;

import com.peasch.jeuxagogo.model.entities.Copy;
import com.peasch.jeuxagogo.model.entities.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BorrowingDto {
    private int id;
    @NotNull
    private UserDto borrowerDto;
    @NotNull
    private CopyDto copyDto;
    @NotNull
    private LocalDate date;
    private LocalDate returnDate;
}
