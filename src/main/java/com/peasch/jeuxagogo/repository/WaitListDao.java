package com.peasch.jeuxagogo.repository;

import com.peasch.jeuxagogo.model.entities.User;
import com.peasch.jeuxagogo.model.entities.WaitList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitListDao extends JpaRepository<WaitList, Integer> {
}
