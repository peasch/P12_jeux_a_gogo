package com.peasch.jeuxagogo.repository;

import com.peasch.jeuxagogo.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {

    User findUserByUsername(String username);
}
