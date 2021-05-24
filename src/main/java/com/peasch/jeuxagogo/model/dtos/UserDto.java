package com.peasch.jeuxagogo.model.dtos;
import com.peasch.jeuxagogo.model.entities.Friendship;
import com.peasch.jeuxagogo.model.entities.Role;
import com.peasch.jeuxagogo.model.entities.User;
import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;

import java.util.Set;

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
    private Set<Role> roles;
    private User godfather;
    private Set<User> godsons;
    private Set<Friendship> askedFriends;
    private Set<Friendship> askerUsers;
}
