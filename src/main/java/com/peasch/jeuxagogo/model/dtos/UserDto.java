package com.peasch.jeuxagogo.model.dtos;
import com.peasch.jeuxagogo.model.entities.Advice;
import com.peasch.jeuxagogo.model.entities.Friendship;
import com.peasch.jeuxagogo.model.entities.Role;
import com.peasch.jeuxagogo.model.entities.User;
import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;

import java.util.HashSet;
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
    private boolean free;

    private LocalDate adhesionDate;
    private Set<RoleDto> rolesDto;
    private UserDto godfather;
    private Set<UserDto> godsons;
    private Set<FriendshipDto> askedFriends;
    private Set<FriendshipDto> askerUsers;
    private Set<AdviceDto> advices = new HashSet<>();
}
