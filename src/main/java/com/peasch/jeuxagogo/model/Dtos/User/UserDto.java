package com.peasch.jeuxagogo.model.Dtos.User;

import com.googlecode.jmapper.annotations.JGlobalMap;
import lombok.*;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String firstname;
    @NotNull @Email
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @Past @NotNull
    private LocalDate birthDate;

    private LocalDate adhesionDate;
}
