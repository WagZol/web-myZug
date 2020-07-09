package com.zoltwagner.myPage.Dto;

import com.zoltwagner.myPage.Constraint.FieldMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@FieldMatch(first = "password", second = "confirmPassword", message = "A jelszavaknak meg kell egyezniük!")

public class UserProfileDto {
	//A dto azaz data transfer object egy olyan objektum ami server client kozott kozvetit adatokat peldanyokon keresztul

    @NotEmpty(message = "A \"felhasználónév\" mező kitöltése kötelező!")
    private String userName;
    //A post-ban erkezo object ezen mezoje validalaskor nem lehet ures, azaz a jelszot ki kell tolteni
    private String password;
    //A post-ban erkezo object ezen mezoje validalaskor nem lehet ures, azaz a jelszo megerositest ki kell tolteni
    private String confirmPassword;



    @NotEmpty(message = "Az \"email\" mező kitöltése kötelező!")
    @Email(message = "Az emailcím érvénytelen!")
    private String email;

    @Pattern(regexp = "^((data:image/(png|jpeg|gif);base64,[^\" \"]+)|)$")
    private String avatar;

    public UserProfileDto(String userName, String email, String password, String avatar) {
        this.userName=userName;
        this.email=email;
        this.password=password;
        this.avatar=avatar;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
