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
@Table(name="editor")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Editor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name="name")
    private String name;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "editor",fetch = FetchType.LAZY)
    private Set<Game> games = new HashSet<>();

    @Column(name = "website")
    private String website;
}
