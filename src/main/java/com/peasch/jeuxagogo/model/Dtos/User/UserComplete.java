package com.peasch.jeuxagogo.model.Dtos.User;

import com.peasch.jeuxagogo.model.entities.Friendship;
import com.peasch.jeuxagogo.model.entities.Role;
import com.peasch.jeuxagogo.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class UserComplete extends UserDto {
    private Set<Role> roles;
    private User godfather;
    private Set<User> godsons;
    private Set<Friendship> askedFriends;
    private Set<Friendship> askerUsers;
}
