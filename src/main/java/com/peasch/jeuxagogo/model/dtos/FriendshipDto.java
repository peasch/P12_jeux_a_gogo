package com.peasch.jeuxagogo.model.dtos;

import com.peasch.jeuxagogo.model.entities.User;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FriendshipDto {
    private int id;
    @NotNull
    private User asked;
    @NotNull
    private User asker;
    private Boolean accepted;
}
