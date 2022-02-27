package com.appDisney.application.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationServices {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Async
    public void sentMail(String body, String matter, String mailTo){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mailTo);
        message.setFrom(mailFrom);  //message.setFrom("noreplay@appdisneyworld.com");
        message.setSubject(matter);
        message.setText(body);

        mailSender.send(message);
    }

}
