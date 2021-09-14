package com.peasch.jeuxagogo.mailing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class EmailService {


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("ssl0.ovh.net");
        mailSender.setPort(465);
        mailSender.setUsername("jeuxagogo@peasch.fr");
        mailSender.setPassword("@5rueMozart57970Yutz");


        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.ssl.enable", true);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", true);
        return mailSender;
    }


    @Autowired
    private JavaMailSender mailSender;

    public void send(String to, String subject, String text) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("jeuxagogo@peasch.fr");
        email.setTo(to);
        email.setSubject(subject);
        email.setText(text);

        mailSender.send(email);

    }


}
