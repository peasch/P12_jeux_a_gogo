package com.peasch.jeuxagogo.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="game")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="age_min")
    private int ageMin;
    @Column(name = "min_players")
    private int minPlayers;
    @Column(name = "max_players")
    private int maxPlayers;
    @Column(name = "available")
    private Boolean available;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "rules_link")
    private String rulesLink;
    @Column(name = "French")
    private Boolean French;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_editor")
    private Editor editor;
    @Column(name="borrowing_quantity")
    private int borrowingQuantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_game_style")
    private GameStyle gameStyle;


    @OneToMany(mappedBy = "game",fetch = FetchType.LAZY)
    private Set<Copy> copies = new HashSet<>();


}
