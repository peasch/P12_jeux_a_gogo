package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.CopyMapper;
import com.peasch.jeuxagogo.model.dtos.CopyDto;
import com.peasch.jeuxagogo.repository.CopyDao;
import com.peasch.jeuxagogo.service.CopyService;
import com.peasch.jeuxagogo.service.GameService;
import com.peasch.jeuxagogo.service.misc.Text_FR;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CopyServiceImpl implements CopyService {

    @Autowired
    private CopyMapper mapper;

    @Autowired
    private CopyDao dao;

    @Autowired
    private GameService gameService;

    //------------------------Métier----------------------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<CopyDto> getAll() {
        return dao.findAll().stream().map(mapper::fromCopyToDtoWithoutGame).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<CopyDto> getCopiesByGameId(int id) {
        return dao.findAllByGame_Id(id).stream().map(mapper::fromCopyToDtoWithoutGame).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<CopyDto> getAvailableCopiesByGameId(int id) {
        return dao.findAllByGame_IdAndAvailable(id).stream().map(mapper::fromCopyToDtoWithoutGame).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CopyDto save(CopyDto copy, int id) {
        copy.setGame(gameService.findById(id));
        copy.setAvailable(true);
        this.validationOfNewCopy(copy);
        return mapper.fromCopyToDtoWithoutGame(dao.save(mapper.fromDtoToCopy(copy)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CopyDto update(CopyDto copy) {
        CopyDto updatecopy = this.findById(copy.getId());
        updatecopy.setGame(copy.getGame());
        updatecopy.setCode(updatecopy.getCode());
        updatecopy.setAvailable(copy.getAvailable());

        CustomConstraintValidation<CopyDto> customConstraintValidation = new CustomConstraintValidation<>();
        customConstraintValidation.validate(updatecopy);
        return mapper.fromCopyToDtoWithoutGame(dao.save(mapper.fromDtoToCopy(copy)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CopyDto setUnavailable(CopyDto copyDto){
        CopyDto borrowedCopy = this.findById(copyDto.getId());
        borrowedCopy.setAvailable(false);
        CustomConstraintValidation<CopyDto> customConstraintValidation = new CustomConstraintValidation<>();
        customConstraintValidation.validate(copyDto);
        return mapper.fromCopyToDtoWithoutGame(dao.save(mapper.fromDtoToCopy(borrowedCopy)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CopyDto setAvailable(CopyDto copyDto){
        CopyDto borrowedCopy = this.findById(copyDto.getId());
        borrowedCopy.setAvailable(true);
        CustomConstraintValidation<CopyDto> customConstraintValidation = new CustomConstraintValidation<>();
        customConstraintValidation.validate(copyDto);
        return mapper.fromCopyToDtoWithoutGame(dao.save(mapper.fromDtoToCopy(borrowedCopy)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(int id) {
        dao.delete(mapper.fromDtoToCopy(this.findById(id)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteAllByGameId(int id) {
        List<CopyDto> copies = this.getCopiesByGameId(id);
        for (CopyDto copy : copies) {
            this.delete(copy.getId());
        }
    }
//--------------------------Findings ----------------------------------------------

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public CopyDto findByCode(String code) {
        return mapper.fromCopyToDtoWithoutGame(dao.findByCode(code));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public CopyDto findById(int id) {
        return mapper.fromCopyToDtoWithoutGame(dao.findById(id).get());
    }


    // ---------------------------------Checking fields --------------------------

    private boolean checkingCode(String code) {
        return this.findByCode(code) != null;
    }

    private boolean checkingGame(int id) {
        return gameService.findById(id) == null;
    }

    //---------------------------------Validation --------------------------------



    private void validationOfNewCopy(CopyDto copyDto) throws ValidationException {
        if (this.checkingGame(copyDto.getGame().getId())) {
            Log.info(Text_FR.NOT_FOUND_GAME);
            throw new ValidationException(Text_FR.NOT_FOUND_GAME);
        }
        if (this.checkingCode(copyDto.getCode())) {
            Log.info(Text_FR.ALREADY_USED_COPY_CODE);
            throw new ValidationException(Text_FR.ALREADY_USED_COPY_CODE);
        }
        CustomConstraintValidation<CopyDto> customConstraintValidation = new CustomConstraintValidation<>();
        customConstraintValidation.validate(copyDto);
    }
}
