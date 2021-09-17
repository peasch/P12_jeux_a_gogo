package com.peasch.jeuxagogo.repository;

import com.peasch.jeuxagogo.model.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface GameDao extends JpaRepository<Game, Integer> {

    Game findGameByName(String name);

    @Query("SELECT DISTINCT ageMin FROM Game ")
    List<Integer> findDistinctByAgeMin();

    @Query("SELECT DISTINCT minPlayers FROM Game ")
    List<Integer> findDistinctByMinPlayers();

    List<Game> findGamesByGameStyle_Id(int id);

    List<Game> findGamesByNameLike(String name);

    List<Game> findGamesByAgeMinEquals(int age);
}

