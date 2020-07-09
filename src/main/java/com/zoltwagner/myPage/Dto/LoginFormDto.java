package com.zoltwagner.myPage.Dto;


import javax.validation.constraints.NotEmpty;

public class LoginFormDto {

    @NotEmpty(message = "A \"felhasználónév\" mező kitöltése kötelező!")
    private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
