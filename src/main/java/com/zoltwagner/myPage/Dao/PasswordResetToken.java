package com.zoltwagner.myPage.Dao;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    public PasswordResetToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    private void setExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, EXPIRATION);
        this.expiryDate=calendar.getTime();
    }

    public PasswordResetToken(String token, User user) {
        setExpiryDate();
        this.token = token;
        this.user = user;
    }

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryDate;


}
