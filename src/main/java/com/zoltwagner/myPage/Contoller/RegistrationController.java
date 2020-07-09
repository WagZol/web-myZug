/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.zoltwagner.myPage.Contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.zoltwagner.myPage.Dto.UserRegistrationDto;
import com.zoltwagner.myPage.Service.UserService;

import javax.validation.Valid;

/**
 *
 * @author Zoltán
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {     

	@Autowired
	UserService userService;

	@ModelAttribute("userToRegister")
	public UserRegistrationDto getUserRegistrationDto() {
		return new UserRegistrationDto();
	}

	@GetMapping
	public String getRegistrationPage() {
		return "registration";
	}

	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("userToRegister") @Valid UserRegistrationDto userToRegister,
									  BindingResult result){

		boolean userExist=userService.getUserModelByUsername(userToRegister.getUserName())!=null;
		if (userExist)
			result.rejectValue("userName", null, "Ez a felhasználónév már foglalt!");

		if (result.hasErrors()){
			return "registration";
		}
		userService.save(userToRegister);
		return "redirect:/registration?state=success";
	}


}

