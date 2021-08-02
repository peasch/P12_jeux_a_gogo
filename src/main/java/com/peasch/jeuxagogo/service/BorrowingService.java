package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.BorrowingDto;


import java.util.List;

public interface BorrowingService {
    List<BorrowingDto> getBorrowings();
    List<BorrowingDto> getUnreturnedBorrowings();
    List<BorrowingDto> getAllPendingBorrowings();
    List<BorrowingDto> getReturnedBorrowings();

    List<BorrowingDto> getBorrowingsByUsername(String username);
    List<BorrowingDto> getReturnedBorrowingsByUsername(String username);
    List<BorrowingDto> getPendingBorrowingsByUsername(String username);
    List<BorrowingDto> getUnreturnedBorrowingsByUsername(String username);
    BorrowingDto add(String username,int gameId);
    void delete(int id);

    BorrowingDto validborrowing(int id);
    BorrowingDto returnborrowing(int id);


}
