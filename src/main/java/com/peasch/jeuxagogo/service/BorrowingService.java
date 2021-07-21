package com.peasch.jeuxagogo.service;

import com.peasch.jeuxagogo.model.dtos.BorrowingDto;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;

public interface BorrowingService {
    BorrowingDto add(UserDto user, GameDto game);
}
