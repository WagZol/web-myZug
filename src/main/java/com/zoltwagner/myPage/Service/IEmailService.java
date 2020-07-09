package com.zoltwagner.myPage.Service;

public interface IEmailService {
    public void sendEmail(String sendTo, String sendFrom, String subject, String message, boolean hasOwnCopy);
    public void sendEmail(String sendTo, String sendFrom, String subject, String message);
}
