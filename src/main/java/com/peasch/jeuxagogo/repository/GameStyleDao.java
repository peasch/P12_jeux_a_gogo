package com.peasch.jeuxagogo.repository;

import com.peasch.jeuxagogo.model.dtos.GameStyleDto;
import com.peasch.jeuxagogo.model.entities.GameStyle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameStyleDao extends JpaRepository<GameStyle,Integer> {

    GameStyle findByName(String name);
}
