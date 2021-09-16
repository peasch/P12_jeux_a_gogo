package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.Mappers.AdviceMapper;
import com.peasch.jeuxagogo.model.dtos.AdviceDto;
import com.peasch.jeuxagogo.repository.AdviceDao;
import com.peasch.jeuxagogo.service.AdviceService;
import com.peasch.jeuxagogo.service.GameService;
import com.peasch.jeuxagogo.service.UserService;
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
public class AdviceServiceimpl implements AdviceService {

    @Autowired
    private AdviceMapper mapper;
    @Autowired
    private AdviceDao dao;
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;


    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<AdviceDto> getAdvices() {
        return dao.findAll().stream().map(mapper::fromAdviceToDto).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public AdviceDto getAdviceById(int id) {
        return mapper.fromAdviceToDto(dao.findById(id).get());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<AdviceDto> getAllAdviceById(int id) {
        return dao.findAllByGame_Id(id).stream()
                .map(mapper::fromAdviceToDto).collect(Collectors.toList());
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AdviceDto add(AdviceDto adviceDto, int id) {
        adviceDto.setGame(gameService.findById(id));
        adviceDto.setUser(userService.findByUsername(adviceDto.getUsername()));
        this.validationOfAdvice(adviceDto);
        return mapper.fromAdviceToDto(dao.save(mapper.fromDtoToAdvice(adviceDto)));

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AdviceDto update(int id, AdviceDto adviceDto) {
        var adviceToUpdate = mapper.fromAdviceToDto(dao.findById(id).get());
        adviceToUpdate.setCommentary(adviceDto.getCommentary());
        adviceToUpdate.setRating(adviceDto.getRating());
        adviceToUpdate.setGame(adviceDto.getGame());
        adviceToUpdate.setUser(adviceDto.getUser());
        adviceToUpdate.setUsername(adviceDto.getUsername());
        return mapper.fromAdviceToDto(dao.save(mapper.fromDtoToAdvice(adviceToUpdate)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(int id) {
        dao.delete(mapper.fromDtoToAdvice(this.getAdviceById(id)));
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Float getRatingOfGameId(int id) {
        List<AdviceDto> adviceList = this.getAllAdviceById(id);
        Float totalRating = 0.0f;
        if (!adviceList.isEmpty())
            for (AdviceDto adviceDto : adviceList) {
                totalRating = totalRating + adviceDto.getRating();
            }
        return totalRating / adviceList.size();
    }


    //---------------------------CHECKING FIELDS ------------------------------
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public AdviceDto findByUserAndGame(int gameId, String username) {
        return mapper.fromAdviceToDto(dao.findAdviceByGame_IdAndUser_Username(gameId, username));
    }

    //-------------------------------VALIDATIONS--------------------------------

    private void validationOfAdvice(AdviceDto adviceDto) throws ValidationException {
        if (this.findByUserAndGame(adviceDto.getGame().getId(), adviceDto.getUser().getUsername()) != null) {
            Log.info(Text_FR.ALREADY_COMMENTED_BY_USER);
            throw new ValidationException(Text_FR.ALREADY_COMMENTED_BY_USER);
        }
        CustomConstraintValidation<AdviceDto> customConstraintValidation = new CustomConstraintValidation<>();
        customConstraintValidation.validate(adviceDto);
    }
}
