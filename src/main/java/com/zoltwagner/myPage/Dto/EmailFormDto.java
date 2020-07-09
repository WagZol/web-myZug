package com.zoltwagner.myPage.Dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailFormDto {

    @NotEmpty(message = "A \"név\" mező kitöltése kötelező!")
    private String name;

    private boolean hasCopy;
    @NotEmpty(message = "A \"feladó email címe\" mező kitöltése kötelező!")
    @Email(message = "Az emailcím érvénytelen!")
    private String emailFrom;
    private String subject;
    @NotEmpty(message = "Az \"üzenet\" mező kitöltése kötelező!")
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getHasCopy() {
        return hasCopy;
    }

    public void setHasCopy(boolean hasCopy) {
        this.hasCopy = hasCopy;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EmailFormDto() {
    }

    public EmailFormDto(String name, boolean hasCopy, String emailFrom, String subject, String message) {
        this.name = name;
        this.hasCopy = hasCopy;
        this.emailFrom = emailFrom;
        this.subject = subject;
        this.message = message;
    }

    @Override
    public String toString() {
        return "EmailRequest [name=" + name + ", surename=" + hasCopy + ", emailFrom=" + emailFrom + ", subject="
                + subject + ", message=" + message + "]";
    }


}
