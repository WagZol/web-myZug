package com.zoltwagner.myPage.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void sendEmail(String sendTo, String sendFrom, String subject, String textMessage, boolean hasOwnCopy) {
        SimpleMailMessage message=this.createMessage(sendTo, sendFrom, subject, textMessage);
        emailSender.send(message);

        if (hasOwnCopy) {
            message.setTo(sendFrom);
            message.setSubject(subject+" másolata");
            emailSender.send(message);
        }
    }

    @Override
    public void sendEmail(String sendTo, String sendFrom, String subject, String textMessage) {
        emailSender.send(this.createMessage(sendTo, sendFrom, subject, textMessage));
    }

    private SimpleMailMessage createMessage(String sendTo, String sendFrom, String subject, String textMessage){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendTo);
        message.setFrom(sendFrom);
        message.setSubject(subject);
        message.setText(textMessage);

        return message;
    }
}
