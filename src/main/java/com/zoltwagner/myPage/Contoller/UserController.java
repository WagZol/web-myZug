/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.zoltwagner.myPage.Contoller;

import com.zoltwagner.myPage.Dao.User;
import com.zoltwagner.myPage.Service.SecurityService;
import com.zoltwagner.myPage.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;


/**
 *
 * @author Zoltán
 */
@Controller
	@RequestMapping("/user")
    public class UserController {
	
	@Autowired
	UserService userService;

	@Autowired
	SecurityService securityService;

	@GetMapping("/changePassword")
	public String checkPasswordResetToken(@RequestParam(required = false, name="token") String token){
		if(!securityService.isPasswordResetTokenValid(token)){
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Invalid passwordreset token!");
		}
		User userDto=userService.getUserModelByPasswordResetToken(token);
		userService.authenticateUser(userDto.getUserName(), userDto.getPassword());
//		Ha a token valid akkor a user automatikusan be lesz jelentkeztetve es a profile oldalra navigalva hogy tudja
//		modositani a jelszavat
		return "redirect:/?page=profile";
	}


}

