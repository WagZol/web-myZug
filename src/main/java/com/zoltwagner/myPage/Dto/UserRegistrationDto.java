package com.zoltwagner.myPage.Dto;

import com.zoltwagner.myPage.Constraint.FieldMatch;

import javax.validation.constraints.*;


@FieldMatch(first = "password", second = "confirmPassword", message = "A jelszavaknak meg kell egyezniük!")

public class UserRegistrationDto {
	//A dto azaz data transfer object egy olyan objektum ami server client kozott kozvetit adatokat peldanyokon keresztul

    //A post-ban erkezo object ezen mezoje validalaskor nem lehet ures, azaz a kersztnevet ki kell tolteni
    @NotEmpty(message = "A \"felhasználónév\" mező kitöltése kötelező!")
    private String userName;
    //A post-ban erkezo object ezen mezoje validalaskor nem lehet ures, azaz a jelszot ki kell tolteni
    @NotEmpty(message = "A \"jelszó\" mező kitöltése kötelező!" )
    private String password;
    //A post-ban erkezo object ezen mezoje validalaskor nem lehet ures, azaz a jelszo megerositest ki kell tolteni
    @NotEmpty(message = "A \"jelszó megerősítése\" mező kitöltése kötelező!")
    private String confirmPassword;
    @NotEmpty(message = "Az \"email\" mező kitöltése kötelező!")
    @Email(message = "Az emailcím érvénytelen!")
    private String email;
    @AssertTrue(message = "A regisztrációhoz a feltétlek elfogadása kötelező!")
    private Boolean terms;
    @Pattern(regexp = "^((data:image/(png|jpeg|gif);base64,[^\" \"]+)|)$")
    private String avatar;
    private Boolean registered;


    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
    }

}
