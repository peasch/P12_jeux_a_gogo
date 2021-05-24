package com.peasch.jeuxagogo.model.dtos;

import com.peasch.jeuxagogo.model.entities.Game;
import lombok.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EditorDto implements Serializable {

    private int id;
    @NotNull
    private String name;
    @NotNull
    private String country;
    private Set<Game> games = new HashSet<>();

    private String website;
}
