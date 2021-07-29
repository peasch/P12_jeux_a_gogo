package com.peasch.jeuxagogo.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="borrowing")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user")
    private User borrower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_copy")
    private Copy copy;

    @Column(name = "borrowing_date")
    private LocalDate date;
    @Column(name = "return_date")
    private LocalDate returnDate;
    @Column(name = "returned")
    private Boolean returned;

}
