package com.peasch.jeuxagogo.repository;

import com.peasch.jeuxagogo.model.entities.Advice;
import com.peasch.jeuxagogo.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdviceDao extends JpaRepository<Advice, Integer> {
}
