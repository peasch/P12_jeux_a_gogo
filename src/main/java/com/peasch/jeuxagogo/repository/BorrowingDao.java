package com.peasch.jeuxagogo.repository;

import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.model.entities.Borrowing;

import com.peasch.jeuxagogo.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingDao extends JpaRepository<Borrowing, Integer> {
    List<Borrowing> findAllByBorrower_UsernameAndReturnDateIsNotNull(String username);
    List<Borrowing> findAllByBorrower_UsernameAndAndDateIsNull(String username);
    List<Borrowing> findAllByBorrower_UsernameAndReturnedIsTrue(String username);
    List<Borrowing> findAllByBorrower_UsernameAndReturnedIsFalse(String username);
    List<Borrowing> findAllByCopy_Game_IdAndAndReturnedIsFalse(int id);
    List<Borrowing> findBorrowingsByReturnedIsFalse();
    List<Borrowing> findBorrowingsByReturnedIsTrue();
    List<Borrowing> findBorrowingsByDateIsNull();
    List<Borrowing> findAllByCopy_Game_Id(int id);
    Borrowing findByCopy_Game_IdAndBorrower_UsernameAndAndReturnedIsFalse(int id, String username);
}
