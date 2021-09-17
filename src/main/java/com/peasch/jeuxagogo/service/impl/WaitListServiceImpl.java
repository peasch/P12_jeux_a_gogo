package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.mailing.EmailService;
import com.peasch.jeuxagogo.model.Mappers.WaitListMapper;
import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.model.dtos.WaitListDto;
import com.peasch.jeuxagogo.repository.WaitListDao;
import com.peasch.jeuxagogo.service.BorrowingService;
import com.peasch.jeuxagogo.service.GameService;
import com.peasch.jeuxagogo.service.UserService;
import com.peasch.jeuxagogo.service.WaitListService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WaitListServiceImpl implements WaitListService {
    private static final String ADMIN = "peaschaming@gmail.com";
    private static final String ADMIN_MESSAGE = "L'email de l'utilisateur est invalide: %s. La restitution du jeu a été effectué, " +
            "sans prevenir la premiere personne de la liste d'attente." +
            "erreur : %s";

    private static final String STARTMESSAGE = "Bonjour, le jeu %s dont vous êtes sur la liste d'attente, " +
            "est à nouveau disponible à la location, vous pouvez le réserver dès à présent sur la fiche du jeu.";

    private static final String SUBJECTMESSAGE = "Le jeu que vous attendez est disponible !!! dépêchez-vous !";


    @Autowired
    private WaitListDao dao;

    @Autowired
    private WaitListMapper mapper;
    @Autowired
    private UserService userService;
    @Autowired
    private GameService gameService;
    @Autowired
    private BorrowingService borrowingService;
    @Autowired
    private EmailService emailService;


    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<WaitListDto> getWaitlists() {
        return dao.findAll().stream().map(mapper::fromWaitListToDtoWithGame).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public WaitListDto getWaitlistById(int id) {
        return mapper.fromWaitListToDtoWithGame(dao.findById(id).get());
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<WaitListDto> getWaitlistByGameId(int id) {
        return dao.findAllByGame_Id(id).stream().map(mapper::fromWaitListToDtoWithGame).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<WaitListDto> getWaitlistByWaiterId(int id) {
        return dao.findAllByWaiter_Id(id).stream().map(mapper::fromWaitListToDtoWithGame).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public WaitListDto addWaitListToGame(int id, String username) {
        LocalDate returnDate;
        GameDto game = gameService.findById(id);
        UserDto waiter = userService.findByUsername(username);
        if (Boolean.TRUE.equals(game.getAvailable())) {
            throw new ValidationException("le jeu est disponible");
        }
        if (this.getWaitlistByWaiterIdAndGameId(id, username) != null) {
            throw new ValidationException("Membre déjà sur la liste d'attente");
        }
        if (borrowingService.getUnreturnedByUserAndGame(id, username) != null) {
            throw new ValidationException("l'utilisateur est déjà en possession du jeu");
        }

        if (!borrowingService.getUnreturnedBorrowingsByGameId(id).isEmpty()) {
            returnDate = borrowingService.getUnreturnedBorrowingsByGameId(id).get(0).getReturnDate();
        } else {
            returnDate = LocalDate.now();
        }

        WaitListDto waitList = WaitListDto.builder().game(game).waiter(waiter).date(LocalDate.now()).returnDate(returnDate).build();

        return mapper.fromWaitListToDtoWithGame(dao.save(mapper.fromDtoToWaitList(waitList)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public WaitListDto getWaitlistByWaiterIdAndGameId(int id, String username) {
        return mapper.fromWaitListToDtoWithGame(dao.findByGame_IdAndWaiter_Username(id, username));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteWaitList(int id) {
        dao.delete(mapper.fromDtoToWaitList(this.getWaitlistById(id)));

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteWaitListAndContactNext(int id) {
        int gameId = this.getWaitlistById(id).getGame().getId();
        dao.delete(mapper.fromDtoToWaitList(this.getWaitlistById(id)));
        this.contactFirstWaiterOfGameId(gameId);

    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void contactFirstWaiterOfGameId(int id) {
        var contactedWaiter = false;

        List<WaitListDto> listOfWaiter = this.getWaitlistByGameId(id);
        do {
            if (!listOfWaiter.isEmpty()) {
                WaitListDto waiterToContact = listOfWaiter.get(0);
                if (waiterToContact.getContactDate() == null) {
                    try {
                        emailService.send(waiterToContact.getWaiter().getEmail(), SUBJECTMESSAGE,
                                String.format(STARTMESSAGE, waiterToContact.getGame().getName()));
                        waiterToContact.setContactDate(LocalDate.now());
                    } catch (Exception e) {
                        emailService.send(ADMIN, "Mail invalide", String.format(ADMIN_MESSAGE,
                                waiterToContact.getWaiter().getEmail(), ExceptionUtils.getStackTrace(e)));
                        waiterToContact.setContactDate(LocalDate.now());
                    }
                    dao.save(mapper.fromDtoToWaitList(waiterToContact));
                    contactedWaiter = true;
                } else if (waiterToContact.getContactDate().isBefore(LocalDate.now().minusDays(5))) {
                    dao.delete(mapper.fromDtoToWaitList(waiterToContact));
                    contactedWaiter = false;
                } else {
                    contactedWaiter = true;
                }
            } else {
                gameService.setAvailable(gameService.findById(id));
            }
        } while (!contactedWaiter && !listOfWaiter.isEmpty());
    }


}
