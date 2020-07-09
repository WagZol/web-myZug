/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.zoltwagner.myPage.Contoller;

import com.zoltwagner.myPage.Dto.LoginFormDto;
import com.zoltwagner.myPage.Dao.User;
import com.zoltwagner.myPage.Service.EmailService;
import com.zoltwagner.myPage.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @author Zoltán
 */
@Controller
@RequestMapping("/")
public class WelcomeController {


    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;


    @ModelAttribute("userToLogin")
    public LoginFormDto userLoginFormDto() {
        return new LoginFormDto();
    }

    @GetMapping
    public String login(SecurityContextHolderAwareRequestWrapper requestWrapper)
    {
        return "basic";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("userToLogin") @Valid LoginFormDto userToLogin,
                            BindingResult result) {;
        User registeredUser = userService.getUserModelByUsername(userToLogin.getUserName());

        if (userToLogin.getPassword() == "") {
            result.rejectValue("password", null, "A \"jelszó\" mező kitöltése kötelező!");
            return "basic :: #login-form";
        }

        if (!userService.checkCredentials(userToLogin.getUserName(), userToLogin.getPassword())) {

            result.rejectValue("userName", null, "Hibás felhasználónév vagy jelszó!");
            result.rejectValue("password", null, "Hibás felhasználónév vagy jelszó!");
            return "basic :: #login-form";
        }

        authenticateUser(userToLogin.getUserName(), userToLogin.getPassword());

        return "basic :: #login-form";
    }

    @PostMapping("/passwordReset")
    public String sendResetPasswordToken(@ModelAttribute("userToLogin") @Valid LoginFormDto userToLogin,
                                         BindingResult result, Model model) {

        if (result.hasErrors())
            return "basic :: #login-modal-body";

        User userWithForgottenPassword = userService.getUserModelByUsername(userToLogin.getUserName());
        if (userWithForgottenPassword == null) {
            result.rejectValue("userName", null,
                    "Nincs ilyen nevű felhasználó az adabázisban!");
        } else {
            String passwordResetToken=UUID.randomUUID().toString();

            emailService.sendEmail(
                    userWithForgottenPassword.getEmail(),
                    "zoltwagner@gmail.com",
                    "Password reset token",
                    "https://myzug.hu/user/changePassword?token="+passwordResetToken
            );

            String succesMessage = "A linket amivel eléri a felhasználója jelszómódosító felületét, a regisztrációkor"+
                    "megadtott email címre sikeresen elküldtük. Ez a link 24 órán át lesz használható";
            model.addAttribute("loginModalMessage", succesMessage);

            userService.createPasswordResetTokenForUser(userWithForgottenPassword, passwordResetToken);
        }

        return "basic :: #login-modal-body";
    }

    private void authenticateUser(String userName, String password) {
        UserDetails userDetails = userService.loadUserByUsername(userName);
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
    }

}
