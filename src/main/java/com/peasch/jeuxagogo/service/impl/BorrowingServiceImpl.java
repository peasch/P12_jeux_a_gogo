package com.peasch.jeuxagogo.service.impl;
import com.peasch.jeuxagogo.model.Mappers.BorrowingMapper;
import com.peasch.jeuxagogo.model.Mappers.UserMapper;
import com.peasch.jeuxagogo.model.dtos.BorrowingDto;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.model.entities.Borrowing;
import com.peasch.jeuxagogo.repository.BorrowingDao;
import com.peasch.jeuxagogo.service.BorrowingService;
import com.peasch.jeuxagogo.service.CopyService;
import com.peasch.jeuxagogo.service.GameService;
import com.peasch.jeuxagogo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BorrowingServiceImpl implements BorrowingService {
    @Autowired
    private BorrowingDao dao;
    @Autowired
    private BorrowingMapper mapper;
    @Autowired
    private GameService gameService;

    @Autowired
    private CopyService copyService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<BorrowingDto> getBorrowings(){
        return dao.findAll().stream().map(mapper::fromBorrowingToDto).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BorrowingDto add(String username, int gameId) {
        BorrowingDto borrowingDto = new BorrowingDto();
        UserDto borrower = userService.findByUsernameWithRoles(username);
        borrowingDto.setBorrowerDto(borrower);
        borrowingDto.setCopyDto(copyService.getAvailableCopiesByGameId(gameId).get(0));
        copyService.setUnavailable(borrowingDto.getCopyDto());
        gameService.setUnavailable(borrowingDto.getCopyDto().getGame());
        return mapper.fromBorrowingToDto(dao.save(mapper.fromDtoToBorrowing(borrowingDto)));

    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BorrowingDto validborrowing(int id){
        BorrowingDto borrowingDto = mapper.fromBorrowingToDto(dao.findById(id).get());
        borrowingDto.setDate(LocalDate.now());
        borrowingDto.setReturnDate(LocalDate.now().plusMonths(1));
        borrowingDto.setReturned(false);
        return mapper.fromBorrowingToDto(dao.save(mapper.fromDtoToBorrowing(borrowingDto)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BorrowingDto returnborrowing(int id){
        BorrowingDto borrowingDto = mapper.fromBorrowingToDto(dao.findById(id).get());
        borrowingDto.setReturned(true);
        copyService.setAvailable(borrowingDto.getCopyDto());
        gameService.setAvailable(borrowingDto.getCopyDto().getGame());
        return mapper.fromBorrowingToDto(dao.save(mapper.fromDtoToBorrowing(borrowingDto)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<BorrowingDto> getUnreturnedBorrowings(){
        return dao.findBorrowingsByReturnedIsFalse().stream().map(mapper::fromBorrowingToDto).collect(Collectors.toList());
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<BorrowingDto> getReturnedBorrowings(){
        return dao.findBorrowingsByReturnedIsTrue().stream().map(mapper::fromBorrowingToDto).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<BorrowingDto> getAllPendingBorrowings(){
        return dao.findBorrowingsByDateIsNull().stream().map(mapper::fromBorrowingToDto).collect(Collectors.toList());
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<BorrowingDto> getBorrowingsByUsername(String username){
        return dao.findAllByBorrower_UsernameAndReturnDateIsNotNull(username).stream().map(mapper::fromBorrowingToDto).collect(Collectors.toList());
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<BorrowingDto> getReturnedBorrowingsByUsername(String username){
        return dao.findAllByBorrower_UsernameAndReturnedIsTrue(username).stream().map(mapper::fromBorrowingToDto).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<BorrowingDto> getPendingBorrowingsByUsername(String username){
        return dao.findAllByBorrower_UsernameAndAndDateIsNull(username).stream().map(mapper::fromBorrowingToDto).collect(Collectors.toList());
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<BorrowingDto> getUnreturnedBorrowingsByUsername(String username){
        return dao.findAllByBorrower_UsernameAndReturnedIsFalse(username).stream().map(mapper::fromBorrowingToDto).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(int id) {
        BorrowingDto borrowingDto = mapper.fromBorrowingToDto(dao.findById(id).get());
        copyService.setAvailable(borrowingDto.getCopyDto());
        dao.delete(mapper.fromDtoToBorrowing(borrowingDto));

    }

}
