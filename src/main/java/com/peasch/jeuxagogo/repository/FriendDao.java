package com.peasch.jeuxagogo.repository;

import com.peasch.jeuxagogo.model.entities.Friendship;
import com.peasch.jeuxagogo.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendDao extends JpaRepository<Friendship, Integer> {
}
