/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.zoltwagner.myPage.Contoller;

import com.zoltwagner.myPage.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.zoltwagner.myPage.Dto.EmailFormDto;

import javax.validation.Valid;

/**
 * @author Zoltán
 */
@Controller
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @GetMapping()
    public String getEmailPage() {
        return "email";
    }

    @ModelAttribute("email")
    public EmailFormDto getEmailFormDto() {
        return new EmailFormDto();
    }

    @PostMapping
    public String sendEmailMessage(@ModelAttribute("email") @Valid EmailFormDto emailFormDto,
                                   BindingResult result) {

        if (result.hasErrors()) {
            return "email";
        }

        emailService.sendEmail(
                "zoltwagner@gmail.com",
                emailFormDto.getEmailFrom(),
                emailFormDto.getSubject(),
                emailFormDto.getMessage() + "\n\n" + emailFormDto.getName()
                        + "\n" + emailFormDto.getEmailFrom(),

                emailFormDto.getHasCopy()
        );

        return "redirect:/email?state=success";

    }

}
