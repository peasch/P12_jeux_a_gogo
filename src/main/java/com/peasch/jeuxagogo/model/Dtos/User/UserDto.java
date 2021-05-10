package com.peasch.jeuxagogo.model.Dtos.User;

import com.googlecode.jmapper.annotations.JGlobalMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

@JGlobalMap
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;

    private String name;
    private String firstname;
    private String email;
    private String username;
    private String password;
    private LocalDate birthDate;
    private LocalDate adhesionDate;
}
