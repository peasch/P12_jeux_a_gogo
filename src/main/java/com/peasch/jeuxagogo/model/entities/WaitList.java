package com.peasch.jeuxagogo.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="wait_list")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class WaitList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_game")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_waiter")
    private User waiter;

    @Column(name = "waitList_date")
    private LocalDate date;
    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "contact_date")
    private LocalDate contactDate;
}
