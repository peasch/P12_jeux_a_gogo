package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.BorrowingDto;

import java.util.List;

public interface BorrowingService {
    //--------------ALL Borrowings -----------------------------------

    List<BorrowingDto> getBorrowings();

    List<BorrowingDto> getUnreturnedBorrowings();

    List<BorrowingDto> getAllPendingBorrowings();

    List<BorrowingDto> getReturnedBorrowings();

    List<BorrowingDto> getBorrowingsToReturnSoon();

    //--------------ALL Borrowings by game----------------------------

    List<BorrowingDto> getAllBorrowingsByGameId(int id);

    List<BorrowingDto> getUnreturnedBorrowingsByGameId(int id);

    //--------------ALL Borrowings by user ---------------------------

    BorrowingDto getUnreturnedByUserAndGame(int id, String username);

    List<BorrowingDto> getBorrowingsByUsername(String username);

    List<BorrowingDto> getReturnedBorrowingsByUsername(String username);

    List<BorrowingDto> getPendingBorrowingsByUsername(String username);

    List<BorrowingDto> getUnreturnedBorrowingsByUsername(String username);

    //--------------Management --------- -----------------------------

    BorrowingDto add(String username, int gameId);

    BorrowingDto addfromWaitList(String username, int gameId);

    void delete(int id);

    BorrowingDto validborrowing(int id);

    BorrowingDto returnborrowing(int id);


}
