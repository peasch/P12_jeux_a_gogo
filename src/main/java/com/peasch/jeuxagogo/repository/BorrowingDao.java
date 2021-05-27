package com.peasch.jeuxagogo.repository;

import com.peasch.jeuxagogo.model.entities.Borrowing;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingDao extends JpaRepository<Borrowing, Integer> {
}
