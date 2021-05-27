package com.peasch.jeuxagogo.repository;

import com.peasch.jeuxagogo.model.entities.Copy;
import com.peasch.jeuxagogo.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CopyDao extends JpaRepository<Copy, Integer> {
}
