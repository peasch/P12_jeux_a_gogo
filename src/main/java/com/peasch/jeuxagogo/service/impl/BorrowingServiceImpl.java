package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.dtos.BorrowingDto;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.repository.GameDao;
import com.peasch.jeuxagogo.service.BorrowingService;
import com.peasch.jeuxagogo.service.CopyService;
import com.peasch.jeuxagogo.service.GameService;
import com.peasch.jeuxagogo.service.UserService;
import com.peasch.jeuxagogo.service.misc.Text_FR;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

@Service
public class BorrowingServiceImpl implements BorrowingService {

    @Autowired
    private GameService gameService;

    @Autowired
    private CopyService copyService;
    @Autowired
    private UserService userService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BorrowingDto add (BorrowingDto borrowingDto){
        UserDto borrower = userService.findByUsername(borrowingDto.getBorrower().getUsername());
        GameDto borrowedCopy = gameService.findById(borrowingDto.getCopy().getId());


    }




    private void validationOfNewBorrowing(BorrowingDto borrowingDto) throws ValidationException {


    }
}
