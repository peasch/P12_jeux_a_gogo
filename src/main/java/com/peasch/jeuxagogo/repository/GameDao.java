package com.peasch.jeuxagogo.repository;

import com.peasch.jeuxagogo.model.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GameDao extends JpaRepository<Game,Integer> {

    Game findGameByName(String name);


}
