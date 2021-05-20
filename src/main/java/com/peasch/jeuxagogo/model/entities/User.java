package com.peasch.jeuxagogo.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Table(name="user")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "birthdate")
    private LocalDate birthDate;

    @Column(name = "adhesion_date")
    private LocalDate adhesionDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_has_role",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")
            }
    )
    private Set < Role > roles = new HashSet <> ();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_godfather")
    private User godfather;

    @OneToMany(mappedBy = "godfather")
    private Set<User> godsons = new HashSet<>();

    @OneToMany(mappedBy = "asked")
    private Set<Friendship> askedFriends = new HashSet<>();

    @OneToMany(mappedBy = "asker")
    private Set<Friendship> askerUsers = new HashSet<>();


}
