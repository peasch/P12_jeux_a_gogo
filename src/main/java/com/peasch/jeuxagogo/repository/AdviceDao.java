package com.peasch.jeuxagogo.repository;

import com.peasch.jeuxagogo.model.dtos.AdviceDto;
import com.peasch.jeuxagogo.model.entities.Advice;
import com.peasch.jeuxagogo.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdviceDao extends JpaRepository<Advice, Integer> {

    Advice findAdviceByGame_IdAndUser_Username(int gameId,String username);
    List<Advice> findAllByGame_Id(int id);
}
