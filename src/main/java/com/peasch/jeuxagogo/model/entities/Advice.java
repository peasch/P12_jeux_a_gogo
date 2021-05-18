package com.peasch.jeuxagogo.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="advice")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Advice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "fk_game")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "fk_user")
    private User user;

    @Column(name = "commentary")
    private String commentary;

    @Column(name = "rating")
    private int rating;
}
