package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.BorrowingDto;


import java.util.List;

public interface BorrowingService {
    List<BorrowingDto> getBorrowings();
    List<BorrowingDto> getBorrowingsByUsername(String username);
    BorrowingDto add(String username,int gameId);
}
