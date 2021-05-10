package com.peasch.jeuxagogo.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="game_style")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class GameStyle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "gameStyle",fetch = FetchType.LAZY)
    private Set<Game> games = new HashSet<>();
}
