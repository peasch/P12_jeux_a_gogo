package com.peasch.jeuxagogo.repository;

import com.peasch.jeuxagogo.model.entities.Copy;
import com.peasch.jeuxagogo.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CopyDao extends JpaRepository<Copy, Integer> {

    List<Copy> findAllByGame_Id(int id);
    Copy findByCode(String code);

    List<Copy> findAllByGameIdAndAvailable(int id,Boolean available);

}
