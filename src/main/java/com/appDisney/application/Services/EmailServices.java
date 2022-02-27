package com.appDisney.application.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServices {

    @Autowired
    private JavaMailSender mailSender;

    public void mailToUsuario(String name, String email, String message){

        SimpleMailMessage message1 = new SimpleMailMessage();
        message1.setFrom("examplefelgnoreply@gmail.com");
        message1.setTo("examplefelgnoreply@gmail.com");
        message1.setSubject("Message received");
        message1.setText(name + ", who has the mail: " + email + ", sent this message: " + message);

        mailSender.send(message1);

        SimpleMailMessage message2 = new SimpleMailMessage();
        message2.setFrom("examplefelgnoreply@gmail.com");
        message2.setTo(email);
        message2.setSubject("Welcome to AppDisney!");
        message2.setText("Thank you "+ name +" for visiting this platform. Enjoyment.");

        mailSender.send(message2);

    }

}