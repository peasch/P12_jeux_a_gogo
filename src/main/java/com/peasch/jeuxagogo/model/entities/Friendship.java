package com.peasch.jeuxagogo.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="friendship")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_asked")
    private User asked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_asker")
    private User asker;

    @Column(name = "accepted")
    private Boolean accepted;
}
