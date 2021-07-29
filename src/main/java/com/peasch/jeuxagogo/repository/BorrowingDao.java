package com.peasch.jeuxagogo.repository;

import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.model.entities.Borrowing;

import com.peasch.jeuxagogo.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingDao extends JpaRepository<Borrowing, Integer> {
    List<Borrowing> findAllByBorrower_Username(String username);
}
