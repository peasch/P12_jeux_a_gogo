package com.peasch.jeuxagogo.repository;

import com.peasch.jeuxagogo.model.entities.WaitList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaitListDao extends JpaRepository<WaitList, Integer> {

    List<WaitList> findAllByGame_Id(int Id);
    List<WaitList> findAllByWaiter_Id(int Id);
    WaitList findByGame_IdAndWaiter_Username(int id, String username);
}
