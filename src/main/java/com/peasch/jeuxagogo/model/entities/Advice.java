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
}
