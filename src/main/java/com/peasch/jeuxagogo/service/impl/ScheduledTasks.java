package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.mailing.EmailService;
import com.peasch.jeuxagogo.model.dtos.BorrowingDto;
import com.peasch.jeuxagogo.model.dtos.UserDto;
import com.peasch.jeuxagogo.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;


@Service
@EnableScheduling
@EnableAsync
public class ScheduledTasks {

    private static final String STARTMESSAGE = "Bonjour, votre emprunt pour le jeu : %s , arrive à son terme le %s . Prenez contact avec nous pour programmer son retour. Merci";
    private static final String SUBJECTMESSAGE = "Votre location de %s expire bientôt!";

    @Autowired
    private EmailService emailService;
    @Autowired
    private BorrowingService borrowingService;

    /*@Scheduled(fixedDelay = 4000)
    @Scheduled(cron = "0 0 11 * * *")
    public void scheduled() {
        List<BorrowingDto> borrowingsToContact = borrowingService.getBorrowingsToReturnSoon();
        if (!borrowingsToContact.isEmpty()) {
            for (BorrowingDto borrowingDto : borrowingsToContact) {
                UserDto user = borrowingDto.getBorrowerDto();
                var gameName = borrowingDto.getCopyDto().getGame().getName();

                emailService.send(user.getEmail(),
                        String.format(SUBJECTMESSAGE, gameName),
                        String.format(STARTMESSAGE, gameName,
                                borrowingDto.getReturnDate()
                                        .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))));
            }
        }
    }*/
}
