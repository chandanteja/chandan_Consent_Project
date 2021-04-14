package com.iiitb.ConsentManagement.ConsentManagement.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }

    public void sendOtpMessage(String to, String subject, String message) {

        System.out.println("Inside sendotpmessage of mailservice");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        logger.info(subject);
        logger.info(to);
        logger.info(message);

        //Uncomment to send mail
        javaMailSender.send(simpleMailMessage);
    }
}
